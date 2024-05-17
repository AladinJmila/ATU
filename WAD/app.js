const express = require('express')
const { engine } = require('express-handlebars')
const path = require('path')
const getDBdata = require('./src/db')
const bodyParser = require('body-parser')

const app = express()
const port = 7777

// Set directory for static files
app.use(express.static(path.join(__dirname, 'public')))

// Read json response
app.use(bodyParser.json())
app.use(bodyParser.urlencoded({ extended: false }))

// Set Handlebars as view engine
app.engine('handlebars', engine())
app.set('view engine', 'handlebars')
app.set('views', './views')

app.get('/home', async (req, res) => {
  let data = await getDBdata('SELECT * FROM products;')
  data = data.map(product => ({ ...product, stars: Math.floor(product.rating) }))

  res.render('home', { data })
})

app.get('/login', async (req, res) => {
  res.render('login')
})

app.post('/login', async (req, res) => {
  let isAuthenticated = false
  const { username, password } = req.body

  try {
    const data = await getDBdata(
      `SELECT password FROM users WHERE username = '${username}'`
    )

    if (data.length) {
      if (data[0].password === password) isAuthenticated = true
    }

    res.send({ isAuthenticated })
  } catch (error) {
    console.log('Database error: ', error)
    res.status(500).send('Internal server error')
  }
})

app.get('/plants/:id', async (req, res) => {
  const id = req.params.id
  const products = await getDBdata(`SELECT * FROM products WHERE product_id = '${id}';`)
  const reviews = await getDBdata(`SELECT * FROM reviews WHERE product_id = '${id}';`)
  const similar = await getDBdata(`SELECT p.name, p.image_url, p.price, p.rating, p.product_id FROM products p
                                  JOIN products_similar_products ps ON ps.product_id = '${id}'
                                  WHERE p.product_id = ps.similar_product_id;`)
  res.render('plant', {
    data: {
      product: { ...products[0], stars: Math.floor(products[0].rating) },
      reviews,
      similar: similar.map(product => ({ ...product, stars: Math.floor(product.rating) }))
    }
  })
})

app.get('/basket', async (req, res) => {
  if (!req.query?.orders || !JSON.parse(req.query.orders).length) {
    return res
      .status(404)
      .render('error', { message: 'Your basket is empty' })
  }
  const orders = JSON.parse(req.query.orders)
  const productsIds = orders.map(product => product?.productId)
  const data = await getDBdata(
    `SELECT * FROM products WHERE product_id IN (${productsIds.join(',')});`
  )
  data.forEach(product => {
    orders.forEach(order => {
      if (order.productId === product.product_id) {
        product.quantity = order.count
      }
    })
  })

  res.render('basket', { data })
})

app.use((req, res) => {
  res
    .status(404)
    .render('error', { message: 'Invalid route' })
})

app.listen(port, () => {
  console.log(`Server running on port ${port}...`)
})
