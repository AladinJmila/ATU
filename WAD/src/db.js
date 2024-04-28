const mysql = require('mysql2/promise')

async function getDBdata(query) {
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

module.exports = getDBdata
