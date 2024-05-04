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
  const basket = JSON.parse(window.localStorage.getItem('basket'))
  const basketCountElement = document.getElementById('basket-items-count')
  const plantElement = document.querySelector('.fa-pagelines')
  if (basket) {
    basketCountElement.innerText = basket.totalCount
    plantElement.classList.add('show')
    if (basket.totalCount >= 10) basketCountElement.classList.add('two-digits')
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
  const basket = JSON.parse(window.localStorage.getItem('basket')) || {
    products: [],
    totalCount: 0
  }
  const existingIndex = basket.products.findIndex(
    item => item.productId === +productId
  )
  if (existingIndex >= 0) {
    basket.products[existingIndex].count++
  } else {
    basket.products.push({ productId: +productId, count: 1 })
  }
  basket.totalCount++
  updateBasketCount(basket.totalCount)
  window.localStorage.setItem('basket', JSON.stringify(basket))
}

function removeFromBasket(productId) {
  const basket = JSON.parse(window.localStorage.getItem('basket')) || []
  const existingIndex = basket.products.findIndex(
    item => item.productId === +productId
  )

  if (existingIndex >= 0) {
    if (basket.products[existingIndex].count > 1) {
      basket.products[existingIndex].count--
    } else {
      basket.products.splice(existingIndex, 1)
    }
    basket.totalCount--
    updateBasketCount(basket.totalCount)
    window.localStorage.setItem('basket', JSON.stringify(basket))
  }
}

// attach get query params to basket page get request
const basketButtons = document.querySelectorAll('[href="/basket/"]')
basketButtons.forEach(button => {
  button.addEventListener('click', e => {
    e.preventDefault()

    const orders = window.localStorage.getItem('basket')
    window.location.href = `/basket/?orders=${orders || ''}`
  })
})

function handleNavLinks() {
  const navLinks = document.querySelectorAll('.nav-link')
  navLinks.forEach(function (link) {
    if (window.location.href.split('?')[0] === link.href) {
      link.classList.add('active')
    }
  })
}

function showOrderCount() {
  const orders = document.querySelectorAll('.basket-product-card')
  const basket = JSON.parse(window.localStorage.getItem('basket'))
  if (orders.length) {
    orders.forEach(order => {
      const basketItem = basket.products.find(
        product => product.productId === +order.dataset.id
      )

      order.querySelector('.order-quantity').innerText = basketItem.count
    })
  }
}

window.addEventListener('load', () => {
  displayBasketCount()
  handleNavLinks()
  showOrderCount()
})
