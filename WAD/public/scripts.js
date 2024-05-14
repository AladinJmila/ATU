/* globals WAD */

window.WAD = {
  init () {
    WAD.checkIfAuthenticated()
    WAD.displayBasketCount()
    WAD.handleNavLinks()
    WAD.showOrderCountAndPrice()
    WAD.handleLogin()
    WAD.handleLogout()
    WAD.handleQuantityChange()
    WAD.handleDeleteFromBasket()
  },

  updateBasketCount (currentCount) {
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

  displayBasketCount () {
    const basket = JSON.parse(window.localStorage.getItem('basket'))
    const basketCountElement = document.getElementById('basket-items-count')
    const plantElement = document.querySelector('.fa-pagelines')
    if (basket.length) {
      basketCountElement.innerText = basket.length
      plantElement.classList.add('show')
      if (basket.length >= 10) {
        basketCountElement.classList.add('two-digits')
      } else basketCountElement.classList.remove('two-digits')
    }
  },

  addToBasket (current, e) {
    e.stopPropagation()
    const { id: productId, price: productPrice } = current.dataset
    const productCard = document.getElementById(productId)

    const basket = JSON.parse(window.localStorage.getItem('basket')) || []
    const existingIndex = basket.findIndex(
      item => item.productId === +productId
    )
    if (existingIndex >= 0) {
      console.log(productCard)
      WAD.showInfoPopup(
        productCard,
        '<p style="font-size: 40px;">⛔</p><p>This item is already in the basket</p>',
        2000
      )
    } else {
      basket.push({
        count: 1,
        productId: +productId,
        productPrice: +productPrice
      })
      WAD.showInfoPopup(
        productCard,
        '<p style="font-size: 40px;">✅</p><p>Item added to basket</p>',
        2000
      )
    }

    WAD.updateBasketCount(basket.length)
    window.localStorage.setItem('basket', JSON.stringify(basket))
  },

  showInfoPopup (parentElement, message, displayDuration) {
    const infoPopup = document.createElement('div')
    infoPopup.classList.add('info-popup')

    const styles = {
      position: 'absolute',
      backgroundColor: 'rgba(0, 0, 0, .7)',
      color: 'var(--custom-color-1)',
      display: 'flex',
      flexDirection: 'column',
      justifyContent: 'end',
      alignItems: 'center',
      padding: '100px 20px',
      zIndex: '10',
      top: '0',
      right: '0',
      width: '100%',
      height: '100%',
      fontWeight: 'bold',
      fontSize: '20px',
      textAlign: 'center'
    }

    Object.assign(infoPopup.style, styles)
    infoPopup.innerHTML = `${message}`

    parentElement.appendChild(infoPopup)
    setTimeout(() => infoPopup.remove(), displayDuration)
  },

  handleRouteToBasket (e) {
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
  },

  handleNavLinks () {
    const navLinks = document.querySelectorAll('.nav-link')
    navLinks.forEach(function (link) {
      if (window.location.href.split('?')[0] === link.href) {
        link.classList.add('active')
      }
    })
  },

  showOrderCountAndPrice () {
    const orders = document.querySelectorAll('.basket-product-card')
    const basket = JSON.parse(window.localStorage.getItem('basket'))
    if (orders.length) {
      orders.forEach(order => {
        const basketItem = basket.find(
          product => product.productId === +order.dataset.id
        )

        if (!basketItem) return

        order.querySelector('.order-quantity').value = basketItem.count
        order.querySelector('.items-count').innerText = `(${
          basketItem.count
        } item${basketItem.count > 1 ? 's' : ''})`

        const subtotalPrice = (basketItem.count * basketItem.productPrice).toFixed(2)
        order.querySelector('.subtotal-price').innerText = subtotalPrice
      })
    }
  },

  handleLogin () {
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
  },

  checkIfAuthenticated () {
    const isAuthenticated = window.localStorage.getItem('isAuthenticated')

    if (window.location.href.includes('/basket') && !isAuthenticated) {
      window.location.href = '/login'
    }
  },

  handleLogout () {
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
  },

  updateBasket (productId, quantity) {
    const basket = JSON.parse(window.localStorage.getItem('basket')) || []
    const existingIndex = basket.findIndex(
      item => item.productId === +productId
    )

    basket[existingIndex].count = quantity

    window.localStorage.setItem('basket', JSON.stringify(basket))
  },

  handleQuantityChange () {
    const inputs = document.querySelectorAll(
      ".basket-product-card .product-info [type='number']"
    )
    if (!inputs || !inputs.length) return

    const basketItems = document.querySelectorAll('.basket-product-card')

    inputs.forEach((input, index) =>
      input.addEventListener('change', () => {
        const productId = basketItems[index].dataset.id
        WAD.updateBasket(productId, input.value)
        WAD.showOrderCountAndPrice()
      })
    )
  },

  removeFromBasket (productId) {
    const basket = JSON.parse(window.localStorage.getItem('basket'))
    const existingIndex = basket.findIndex(
      item => item.productId === +productId
    )

    basket.splice(existingIndex, 1)

    WAD.updateBasketCount(basket.length)
    window.localStorage.setItem('basket', JSON.stringify(basket))
  },

  handleDeleteFromBasket () {
    const deleteButtons = document.querySelectorAll('.delete-basket-product')
    if (!deleteButtons || !deleteButtons.length) return

    const basketItems = document.querySelectorAll('.basket-product-card')

    deleteButtons.forEach((button, index) => {
      button.addEventListener('click', () => {
        const productId = basketItems[index].dataset.id
        WAD.removeFromBasket(productId)
        basketItems[index].remove()
        // handleDeleteFromBasket()
        const orders = window.localStorage.getItem('basket')
        const currentURL = window.location.href.split('?')[0]
        const updatedURL = currentURL + `?orders=${orders || ''}`

        window.history.pushState({ path: updatedURL }, '', updatedURL)
      })
    })
  },

  getProductDetails (current) {
    const productId = current.id
    window.location.href = `/plants/${productId}`
  },

  openPurchaseModal () {
    const purchaseSummaryModal = document.getElementById('purchase-summary-modal')
    const purchaseSummary = document.getElementById('purchase-summary')
    const basketItems = document.querySelectorAll('.basket-product-card')
    let SummaryHTML = "<h2>Here's your order summary</h2>"
    let totalPrice = 0

    basketItems.forEach(item => {
      const summaryItemHTML = `
      <div class="summary-item">
        ${item.querySelector('img').outerHTML}
        <h5>${item.querySelector('h4').innerText}</h5>
        ${item.querySelector('.product-price').outerHTML}
      </div>`
      const subtotalPrice = +item.querySelector('.subtotal-price').innerText
      totalPrice += subtotalPrice
      SummaryHTML += summaryItemHTML
    })

    SummaryHTML += `<h3 class="total-price">Total Price:<span>&euro;${totalPrice.toFixed(2)}<span></h3>`
    purchaseSummary.insertAdjacentHTML('afterbegin', SummaryHTML)

    purchaseSummaryModal.classList.add('show')
    purchaseSummaryModal.scrollIntoView()
    WAD.bodyFreezeScroll()
  },

  closePurchaseModal () {
    const purchaseSummaryModal = document.getElementById('purchase-summary-modal')

    purchaseSummaryModal.classList.remove('show')
    WAD.bodyUnfreezeScroll()
  },

  confirmPurchase (current) {
    const confirmPurchaseContainer = document.getElementById('confirm-purchase')
    const shoppingBasketContainer = document.getElementById('shopping-basket-container')
    confirmPurchaseContainer.classList.add('show')
    confirmPurchaseContainer.scrollIntoView({ block: 'nearest', behavior: 'smooth' })

    if (current.innerText === 'Confirm purchase') {
      confirmPurchaseContainer.classList.remove('show')
      console.log(shoppingBasketContainer)
      shoppingBasketContainer.innerHTML = '<h1 class="thank-you">Thank you for shopping with us!</h1>'
      setTimeout(() => {
        shoppingBasketContainer.innerHTML = ''
        window.location.href = '/home'
      }, 3000)
      WAD.closePurchaseModal()
      current.innerText = 'Pay'
    } else {
      current.innerText = 'Confirm purchase'
    }
  },

  bodyFreezeScroll () {
    document.body.style.overflow = 'hidden'
  },

  bodyUnfreezeScroll () {
    document.body.style.overflow = ''
  },

  validateEmail (current) {
    const isValid = String(current.value)
      .toLowerCase()
      .match(
        /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|.(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/
      )

    if (isValid) {
      current.style.color = 'green'
    } else {
      current.style.color = 'red'
    }
  }
}

window.addEventListener('load', WAD.init)
