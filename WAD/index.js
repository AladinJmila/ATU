const express = require('express')
const { engine } = require('express-handlebars')
const mysql = require('mysql2/promise')

const app = express()
const port = 7777

async function getDBdata (query) {
  const connection = await mysql.createConnection({
    host: 'localhost',
    user: 'root',
    password: 'root',
    database: 'g00438809'
  })

  try {
    const [result] = await connection.query(query)

    return result
  } catch (err) {
    console.log(err)
  }
}

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
