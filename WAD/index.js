const express = require('express')
const { engine } = require('express-handlebars')
const path = require('path')
const getDBdata = require('./src/db')

const app = express()
const port = 7777

// Set directory for static files
app.use(express.static(path.join(__dirname, 'public')))

// Set Handlebars as view engine
app.engine('handlebars', engine())
app.set('view engine', 'handlebars')
app.set('views', './views')

app.get('/', async (req, res) => {
  const data = await getDBdata('SELECT first_name, last_name FROM test;')
  console.log(data)
  res.render('home', { data })
})

app.get('/plants/:id', async (req, res) => {
  const id = req.params.id
  console.log(id)
  // const data = await getDBdata('SELECT first_name, last_name FROM test;')
  // console.log(data)
  res.render('plant', {})
})

app.listen(port, () => {
  console.log(`Server running on port ${port}...`)
})
