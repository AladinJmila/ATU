const mysql = require('mysql2/promise')

// Async function to execute a database query and retrieve data
async function getDBdata (query) {
  // Establish a connection to the MySQL database
  const connection = await mysql.createConnection({
    host: 'localhost',
    user: 'root',
    password: 'root',
    database: 'g00438809'
  })

  try {
    // Execute the provided SQL query and retrieve the result
    const [result] = await connection.query(query)

    // Return the result of the query execution
    return result
  } catch (err) {
    // Log any errors that occur during query execution
    console.log(err)
  } finally {
    // Close the database connection after query execution
    await connection.end()
  }
}

module.exports = getDBdata
