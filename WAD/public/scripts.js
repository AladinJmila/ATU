function updateBasketCount(currentCount) {
  const basketCountElement = document.getElementById('basket-items-count')
  const plantElement = document.querySelector('.fa-pagelines')

  if (!currentCount) {
    basketCountElement.innerText = ''
    plantElement.classList.remove('show')
    window.localStorage.removeItem('basketCount')
    return
  }

  basketCountElement.innerText = currentCount
  window.localStorage.setItem('basketCount', currentCount)

  if (currentCount >= 10) basketCountElement.classList.add('two-digits')
  else basketCountElement.classList.remove('two-digits')

  plantElement.classList.add('show')
}

function displayBasketCount() {
  const basket = JSON.parse(window.localStorage.getItem('basket')) || []
  const basketCountElement = document.getElementById('basket-items-count')
  const plantElement = document.querySelector('.fa-pagelines')
  const currentCount = basket.length
  if (currentCount) {
    basketCountElement.innerText = currentCount
    plantElement.classList.add('show')
    if (currentCount >= 10) basketCountElement.classList.add('two-digits')
    else basketCountElement.classList.remove('two-digits')
  }
}

const addToBasketButtons = document.querySelectorAll('.product-info .btn')
addToBasketButtons.forEach(button => {
  button.addEventListener('click', () => {
    addToBasket(button.parentNode.parentNode.dataset.id)
  })
})

function addToBasket(productId) {
  const basket = JSON.parse(window.localStorage.getItem('basket')) || []
  basket.push(+productId)
  updateBasketCount(basket.length)
  window.localStorage.setItem('basket', JSON.stringify(basket))
}

function removeFromBasket(productId) {
  const basket = JSON.parse(window.localStorage.getItem('basket')) || []
  const index = basket.indexOf(productId)
  console.log('index ', index)

  if (index !== -1) {
    console.log('before ', basket.length)
    basket.splice(index, 1)
    console.log('after ', basket.length)
    updateBasketCount(basket.length)
    window.localStorage.setItem('basket', JSON.stringify(basket))
  }
}

window.addEventListener('load', displayBasketCount)
