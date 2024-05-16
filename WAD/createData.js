const fs = require('fs')
const data = require('./generated_db.json')

function generateInsertStatements (plants) {
  const insertPlantStatements = []
  const insertReviewStatements = []
  const insertSimilarStatements = []

  plants.forEach(plant => {
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

    insertPlantStatements.push(insertPlantSQL)

    plant.reviews.forEach(review => {
      const insertReviewSQL = `INSERT INTO reviews (username, rating, comment, product_id) VALUES (
        '${review.username.replace(/'/g, "''")}', 
        ${review.rating}, 
        '${review.comment.replace(/'/g, "''")}',
        ${plant.id}
      );`

      insertReviewStatements.push(insertReviewSQL)
    })

    plant.similar_products.forEach(similar => {
      const insertSimilarSQL = `INSERT INTO products_similar_products (product_id, similar_product_id) VALUES (
        ${plant.id},
        ${Math.floor(Math.random() * 27) + 1}
      );`

      insertSimilarStatements.push(insertSimilarSQL)
    })
  })

  return {
    // plants: insertPlantStatements.join('\n'),
    reviews: insertReviewStatements.join('\n'),
    similar: insertSimilarStatements.join('\n')
  }
}

const sqlStatements = generateInsertStatements(data)
// fs.writeFileSync('products.sql', sqlStatements.plants)
fs.writeFileSync('reviews.sql', sqlStatements.reviews)
fs.writeFileSync('similar.sql', sqlStatements.similar)
