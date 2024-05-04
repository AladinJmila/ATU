const fs = require('fs')
const data = require('./generated_db.json')

function generateInsertStatements(plants) {
  const insertPlantStatements = []
  const insertReviewStatements = []
  const insertSimilarStatements = []

  data.forEach(plant => {
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
      const insertReviewSQL = `INSERT INTO Products (username, quantity_in_stock, description, price, image_url, category, height, light_requirements, care_difficulty, indoor, outdoor, air_purifying, pet_friendly, low_maintenance) VALUES (
        '${review.username.replace(/'/g, "''")}', 
        ${review.rating}, 
        '${review.comment.replace(/'/g, "''")}'
      );`

      insertReviewStatements.push({
        insertReviewSQL,
        name: plant.name.split('(')[0].trim()
      })
    })

    plant.similar_products.forEach(similar => {
      // const insertSimilarSQL = `INSERT INTO Products (name, quantity_in_stock, description, price, image_url, category, height, light_requirements, care_difficulty, indoor, outdoor, air_purifying, pet_friendly, low_maintenance) VALUES (
      //   '${similar.username.replace(/'/g, "''")}',
      //   ${similar.rating},
      //   '${similar.comment.replace(/'/g, "''")}'
      // );`

      insertSimilarStatements.push({
        insertSimilarSQL: '',
        name: plant.name.split('(')[0].trim(),
        similar: similar.name.split('(')[0].trim()
      })
    })
  })

  return {
    plants: insertPlantStatements.join('\n'),
    review: insertReviewStatements,
    similar: insertSimilarStatements
  }
}

const sqlStatements = generateInsertStatements(data)
console.log(sqlStatements.similar)
fs.writeFileSync('products.sql', sqlStatements.plants)
