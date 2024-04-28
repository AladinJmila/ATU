const express = require('express')
const { engine } = require('express-handlebars')
const getDBdata = require('./src/db')

const app = express()
const port = 7777
app.use(express.static('static'))

app.engine('handlebars', engine())
app.set('view engine', 'handlebars')
app.set('views', './views')

app.get('/', async (req, res) => {
  const data = await getDBdata('SELECT first_name, last_name FROM test;')
  console.log(data)
  res.render('home', { data })
})

app.listen(port, () => {
  console.log(`Server running on port ${port}...`)
})
