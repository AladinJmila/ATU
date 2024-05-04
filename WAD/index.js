const express = require('express')
const { engine } = require('express-handlebars')
const path = require('path')
const getDBdata = require('./src/db')
const { copyFileSync } = require('fs')

const app = express()
const port = 7777

// Set directory for static files
app.use(express.static(path.join(__dirname, 'public')))

// Set Handlebars as view engine
app.engine('handlebars', engine())
app.set('view engine', 'handlebars')
app.set('views', './views')

app.get('/', async (req, res) => {
  const data = await getDBdata('SELECT * FROM products;')
  // console.log(data)
  res.render('home', { data })
})

app.get('/plants/:id', async (req, res) => {
  const id = req.params.id
  // console.log(id)
  // const data = await getDBdata('SELECT first_name, last_name FROM test;')
  // console.log(data)
  res.render('plant', {})
})

app.get('/basket/', async (req, res) => {
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
  // console.log(id)
  // const data = await getDBdata('SELECT first_name, last_name FROM test;')
  // console.log(data)
  res.render('basket', { data })
})

app.listen(port, () => {
  console.log(`Server running on port ${port}...`)
})
