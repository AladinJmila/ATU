const fs = require('fs')
const data = require('./generated_db.json')

// Function to generate SQL insert statements for plants, reviews, and similar products
function generateInsertStatements (plants) {
  // Arrays to store insert statements for plants, reviews, and similar products
  const insertPlantStatements = []
  const insertReviewStatements = []
  const insertSimilarStatements = []

  // Iterate through each plant in the data
  plants.forEach(plant => {
    // Generate SQL insert statement for the plant
    const insertPlantSQL = `INSERT INTO Products (name, quantity_in_stock, description, price, rating, image_url, category, height, light_requirements, care_difficulty, indoor, outdoor, air_purifying, pet_friendly) VALUES (
      '${plant.name.replace(/'/g, "''")}', 
      ${plant.quantity_in_stock}, 
      '${plant.description.replace(/'/g, "''")}', 
      ${plant.price}, 
      ${0.0}, 
      '${plant.image_url}', 
      '${plant.category}', 
      '${plant.height}', 
      '${plant.light_requirements}', 
      '${plant.care_difficulty}', 
      ${plant.features.indoor ? 1 : 0}, 
      ${plant.features.outdoor ? 1 : 0}, 
      ${plant.features.air_purifying ? 1 : 0}, 
      ${plant.features.pet_friendly ? 1 : 0}
    );`

    // Push the plant insert statement to the array
    insertPlantStatements.push(insertPlantSQL)

    // Iterate through each review of the plant
    plant.reviews.forEach(review => {
      // Generate SQL insert statement for the review
      const insertReviewSQL = `INSERT INTO reviews (username, rating, comment, product_id) VALUES (
        '${review.username.replace(/'/g, "''")}', 
        ${review.rating}, 
        '${review.comment.replace(/'/g, "''")}',
        ${plant.id}
      );`

      // Push the review insert statement to the array
      insertReviewStatements.push(insertReviewSQL)
    })

    // Iterate through each similar product of the plant
    plant.similar_products.forEach(similar => {
      // Generate SQL insert statement for the similar product
      const insertSimilarSQL = `INSERT INTO products_similar_products (product_id, similar_product_id) VALUES (
        ${plant.id},
        ${Math.floor(Math.random() * 27) + 1}
      );`

      // Push the similar product insert statement to the array
      insertSimilarStatements.push(insertSimilarSQL)
    })
  })

  // Return an object containing the generated SQL statements for plants, reviews, and similar products
  return {
    plants: insertPlantStatements.join('\n'),
    reviews: insertReviewStatements.join('\n'),
    similar: insertSimilarStatements.join('\n')
  }
}

// Generate SQL insert statements
const sqlStatements = generateInsertStatements(data)

// Write the generated SQL statements to separate files
fs.writeFileSync('products.sql', sqlStatements.plants)
fs.writeFileSync('reviews.sql', sqlStatements.reviews)
fs.writeFileSync('similar.sql', sqlStatements.similar)
