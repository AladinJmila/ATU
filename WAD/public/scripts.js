window.WAD = {
  updateBasketCount(currentCount) {
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
  },

  displayBasketCount() {
    const basket = JSON.parse(window.localStorage.getItem('basket'))
    const basketCountElement = document.getElementById('basket-items-count')
    const plantElement = document.querySelector('.fa-pagelines')
    if (basket) {
      basketCountElement.innerText = basket.totalCount
      plantElement.classList.add('show')
      if (basket.totalCount >= 10) {
        basketCountElement.classList.add('two-digits')
      } else basketCountElement.classList.remove('two-digits')
    }
  },

  addToBasket(current) {
    const { id: productId, price: productPrice } = current.dataset

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
    WAD.updateBasketCount(basket.totalCount)
    window.localStorage.setItem('basket', JSON.stringify(basket))
  },

  handleRouteToBasket(e) {
    console.log(e)
    e.preventDefault()

    console.log('here')

    let isAuthenticated = window.localStorage.getItem('isAuthenticated')
    if (isAuthenticated) isAuthenticated = parseInt(isAuthenticated)

    console.log(isAuthenticated)

    if (!isAuthenticated) {
      window.location.href = '/login'
      window.localStorage.setItem('goToBasket', 1)
      return
    }

    const orders = window.localStorage.getItem('basket')
    window.location.href = `/basket?orders=${orders || ''}`
  }
}

// attach get query params to basket page get request
// const basketButtons = document.querySelectorAll('[href="/basket"]')
// basketButtons.forEach(button => {
//   button.addEventListener('click', e => {
//     e.preventDefault()

//     let isAuthenticated = window.localStorage.getItem('isAuthenticated')
//     if (isAuthenticated) isAuthenticated = parseInt(isAuthenticated)

//     console.log(isAuthenticated)

//     if (!isAuthenticated) {
//       window.location.href = '/login'
//       window.localStorage.setItem('goToBasket', 1)
//       return
//     }

//     const orders = window.localStorage.getItem('basket')
//     window.location.href = `/basket?orders=${orders || ''}`
//   })
// })

function handleNavLinks() {
  const navLinks = document.querySelectorAll('.nav-link')
  navLinks.forEach(function (link) {
    if (window.location.href.split('?')[0] === link.href) {
      link.classList.add('active')
    }
  })
}

function showOrderCountAndPrice() {
  const orders = document.querySelectorAll('.basket-product-card')
  const basket = JSON.parse(window.localStorage.getItem('basket'))
  if (orders.length) {
    orders.forEach(order => {
      const basketItem = basket.products.find(
        product => product.productId === +order.dataset.id
      )

      if (!basketItem) return

      order.querySelector('.order-quantity').value = basketItem.count
      order.querySelector('.items-count').innerText = `(${
        basketItem.count
      } item${basketItem.count > 1 ? 's' : ''})`

      const unitPrice = parseInt(order.querySelector('.unit-price').innerText)
      if (unitPrice) {
        order.querySelector('.subtotal-price').innerText =
          basketItem.count * unitPrice
      }
    })
  }
}

function handleLogin() {
  const loginForm = document.getElementById('login-form')
  if (!loginForm) return
  loginForm.addEventListener('submit', async e => {
    e.preventDefault()

    const username = loginForm.querySelector('#username').value
    const password = loginForm.querySelector('#password').value

    try {
      const res = await fetch('/login', {
        method: 'POST',
        body: JSON.stringify({ username, password }),
        headers: {
          'Content-Type': 'application/json'
        }
      })
      const data = await res.json()

      if (data && data.isAuthenticated) {
        const goToBasket = window.localStorage.getItem('goToBasket')
        window.localStorage.setItem('isAuthenticated', 1)

        if (goToBasket && parseInt(goToBasket)) {
          const orders = window.localStorage.getItem('basket')
          window.location.href = `/basket?orders=${orders || ''}`
          window.localStorage.removeItem('goToBasket')
        } else {
          window.location.href = '/home'
        }

        const logoutElement = document.querySelector("[href='/logout']")
        const loginElment = document.querySelector("[href='/login']")

        logoutElement.style.display = 'block'
        loginElment.style.display = 'none'
      }
    } catch (error) {
      console.log(error)
    }
  })
}

function checkIfAuthenticated() {
  const isAuthenticated = window.localStorage.getItem('isAuthenticated')

  if (window.location.href.includes('/basket') && !isAuthenticated) {
    window.location.href = '/login'
  }
}

function handleLogout() {
  const logoutElement = document.querySelector("[href='/logout']")
  const loginElement = document.querySelector("[href='/login']")

  const isAuthenticated = window.localStorage.getItem('isAuthenticated')

  if (isAuthenticated && parseInt(isAuthenticated)) {
    logoutElement.style.display = 'block'
    loginElement.style.display = 'none'
  }

  logoutElement.addEventListener('click', e => {
    e.preventDefault()

    window.localStorage.removeItem('isAuthenticated')
    window.location.href = '/login'
    loginElement.style.display = 'block'
    logoutElement.style.display = 'none'
  })
}

function updateBasket(productId, quantity) {
  const basket = JSON.parse(window.localStorage.getItem('basket'))
  const existingIndex = basket.products.findIndex(
    item => item.productId === +productId
  )

  basket.products[existingIndex].count = quantity

  basket.totalCount = basket.products.reduce(
    (acc, { count }) => acc + +count,
    0
  )
  updateBasketCount(basket.totalCount)
  window.localStorage.setItem('basket', JSON.stringify(basket))
}

function handleQuantityChange() {
  const inputs = document.querySelectorAll(
    ".basket-product-card .product-info [type='number']"
  )
  if (!inputs || !inputs.length) return

  const basketItems = document.querySelectorAll('.basket-product-card')

  inputs.forEach((input, index) =>
    input.addEventListener('change', () => {
      const productId = basketItems[index].dataset.id
      updateBasket(productId, input.value)
      showOrderCountAndPrice()
    })
  )
}

function removeFromBasket(productId) {
  const basket = JSON.parse(window.localStorage.getItem('basket'))
  const existingIndex = basket.products.findIndex(
    item => item.productId === +productId
  )

  basket.products.splice(existingIndex, 1)
  console.log(basket)

  basket.totalCount = basket.products.reduce(
    (acc, { count }) => acc + +count,
    0
  )
  updateBasketCount(basket.totalCount)
  window.localStorage.setItem('basket', JSON.stringify(basket))
}

function handleDeleteFromBasket() {
  const deleteButtons = document.querySelectorAll('.delete-basket-product')
  if (!deleteButtons || !deleteButtons.length) return

  const basketItems = document.querySelectorAll('.basket-product-card')

  deleteButtons.forEach((button, index) => {
    button.addEventListener('click', () => {
      const productId = basketItems[index].dataset.id
      removeFromBasket(productId)
      basketItems[index].remove()
      // handleDeleteFromBasket()
      const orders = window.localStorage.getItem('basket')
      const currentURL = window.location.href.split('?')[0]
      const updatedURL = currentURL + `?orders=${orders || ''}`

      window.history.pushState({ path: updatedURL }, '', updatedURL)
    })
  })
}

window.addEventListener('load', () => {
  checkIfAuthenticated()
  WAD.displayBasketCount()
  handleNavLinks()
  showOrderCountAndPrice()
  handleLogin()
  handleLogout()
  handleQuantityChange()
  handleDeleteFromBasket()
})
