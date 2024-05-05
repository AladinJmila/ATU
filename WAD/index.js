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
  const data = await getDBdata('SELECT * FROM products;')
  // console.log(data)
  res.render('home', { data })
})

app.get('/login', async (req, res) => {
  res.render('login')
})

app.post('/login', async (req, res) => {
  let isAuthenticated = false
  const { username, password } = req.body
  console.log(username, password)

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
  // const id = req.params.id
  // console.log(id)
  // const data = await getDBdata('SELECT first_name, last_name FROM test;')
  // console.log(data)
  res.render('plant', {})
})

app.get('/basket', async (req, res) => {
  if (!req.query?.orders) {
    return res
      .status(404)
      .render('error', { message: 'Please select at least one item to' })
  }
  const orders = JSON.parse(req.query.orders)
  const productsIds = orders.products.map(product => product.productId)
  console.log(productsIds.join(','))
  const data = await getDBdata(
    `SELECT * FROM products WHERE product_id IN (${productsIds.join(',')});`
  )
  res.render('basket', { data })
})

app.listen(port, () => {
  console.log(`Server running on port ${port}...`)
})
