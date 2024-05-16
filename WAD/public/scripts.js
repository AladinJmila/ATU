/* globals WAD */

window.WAD = {
  // Function to initialize the application by executing essential setup tasks
  init () {
    // Check if the user is authenticated and redirect if necessary
    WAD.checkIfAuthenticated()

    // Display the current basket count in the UI
    WAD.displayBasketCount()

    // Handle navigation links to highlight the active link based on the current page
    WAD.handleNavLinks()

    // Update and display the order count and prices in the basket
    WAD.showOrderCountAndPrice()

    // Configure logout functionality and update UI accordingly
    WAD.handleLogout()
  },

  // Function to update the basket count displayed on the navigation bar
  updateBasketCount (currentCount) {
    // Retrieve the HTML element displaying the basket count
    const basketCountElement = document.getElementById('basket-items-count')
    // Retrieve the HTML element representing the basket icon
    const plantElement = document.querySelector('.fa-pagelines')

    // Check if the currentCount is falsy
    if (!currentCount) {
      // Clear the text content of the basket count element
      basketCountElement.innerText = ''
      // Remove the 'show' class from the plant icon
      plantElement.classList.remove('show')
      // Remove the 'basketCount' key from localStorage
      window.localStorage.removeItem('basketCount')
      // Exit the function early
      return
    }

    // Update the text content of the basket count element with currentCount
    basketCountElement.innerText = currentCount
    // Store the currentCount in localStorage under the 'basketCount' key
    window.localStorage.setItem('basketCount', currentCount)

    // Add the 'two-digits' class to the basket count element if currentCount is 10 or more
    if (currentCount >= 10) basketCountElement.classList.add('two-digits')
    // Remove the 'two-digits' class if currentCount is less than 10
    else basketCountElement.classList.remove('two-digits')

    // Display the plant icon by adding the 'show' class to the plant element
    plantElement.classList.add('show')
  },

  // Function to display the basket count on the navigation bar
  displayBasketCount () {
    // Retrieve the basket items from localStorage and parse them as JSON
    const basket = JSON.parse(window.localStorage.getItem('basket'))
    // Retrieve the HTML element displaying the basket count
    const basketCountElement = document.getElementById('basket-items-count')
    // Retrieve the HTML element representing the plant icon in the basket
    const plantElement = document.querySelector('.fa-pagelines')

    // Check if basket array is not empty
    if (basket.length) {
      // Update the text content of the basket count element with the length of basket array
      basketCountElement.innerText = basket.length
      // Display the plant icon by adding the 'show' class to the plant element
      plantElement.classList.add('show')

      // Add or remove the 'two-digits' class based on the basket length
      if (basket.length >= 10) basketCountElement.classList.add('two-digits')
      else basketCountElement.classList.remove('two-digits')
    }
  },

  // Function to add a product to the basket
  addToBasket (current, e) {
    // Prevent event propagation to parent elements
    e.stopPropagation()

    // Destructure product data attributes from the current element's dataset
    const { id: productId, price: productPrice } = current.dataset

    // Retrieve the product card element based on the productId
    const productCard = document.getElementById(productId)

    // Retrieve basket items from localStorage or initialize as an empty array
    const basket = JSON.parse(window.localStorage.getItem('basket')) || []

    // Check if the product already exists in the basket
    const existingIndex = basket.findIndex(item => item.productId === +productId)

    // If product exists in the basket
    if (existingIndex >= 0) {
      // Show an information popup indicating the item is already in the basket
      WAD.showInfoPopup(
        productCard,
        '<p style="font-size: 40px;">⛔</p><p>This item is already in the basket</p>',
        2000
      )
    } else {
      // If product is not in the basket, add it with count of 1
      basket.push({
        count: 1,
        productId: +productId,
        productPrice: +productPrice
      })

      // Show an information popup indicating the item has been added to the basket
      WAD.showInfoPopup(
        productCard,
        '<p style="font-size: 40px;">✅</p><p>Item added to basket</p>',
        2000
      )
    }

    // Update the basket count displayed on the webpage
    WAD.updateBasketCount(basket.length)

    // Update the basket items in localStorage
    window.localStorage.setItem('basket', JSON.stringify(basket))
  },

  // Function to display an information popup message on a specified element
  showInfoPopup (parentElement, message, displayDuration) {
    // Create a new div element for the info popup
    const infoPopup = document.createElement('div')
    infoPopup.classList.add('info-popup')

    // Define styles for the info popup (an example of styling at the JS code level)
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

    // Apply styles to the info popup element
    Object.assign(infoPopup.style, styles)

    // Set the inner HTML content of the info popup with the provided message
    infoPopup.innerHTML = `${message}`

    // Append the info popup to the specified parent element
    parentElement.appendChild(infoPopup)

    // Automatically remove the info popup after the specified display duration
    setTimeout(() => infoPopup.remove(), displayDuration)
  },

  // Function to handle routing to the basket page
  handleRouteToBasket (e) {
    // Prevent the default behavior of the event (following the clicked link)
    e.preventDefault()

    // Retrieve the authentication status from localStorage and parse as integer if present
    let isAuthenticated = window.localStorage.getItem('isAuthenticated')
    if (isAuthenticated) isAuthenticated = parseInt(isAuthenticated)

    // If user is not authenticated
    if (!isAuthenticated) {
      // Redirect to the login page
      window.location.href = '/login'
      // Set a localStorage flag to indicate redirect to basket after login
      window.localStorage.setItem('goToBasket', 1)
      // Exit the function early
      return
    }

    // Retrieve basket orders from localStorage
    const orders = window.localStorage.getItem('basket')

    // Redirect to the basket page with orders query parameter
    window.location.href = `/basket?orders=${orders || ''}`
  },

  // Function to handle navigation link styling based on current URL
  handleNavLinks () {
    // Retrieve all navigation links with class 'nav-link'
    const navLinks = document.querySelectorAll('.nav-link')

    // Loop through each navigation link
    navLinks.forEach(function (link) {
      // Check if the link's href matches the current page URL (excluding query parameters)
      if (window.location.href.split('?')[0] === link.href) {
        // Add the 'active' class to the matched navigation link
        link.classList.add('active')
      }
    })
  },

  // Function to display order count and subtotal price for each basket product card
  showOrderCountAndPrice () {
    // Retrieve all basket product cards from the DOM
    const orders = document.querySelectorAll('.basket-product-card')

    // Retrieve basket items from localStorage
    const basket = JSON.parse(window.localStorage.getItem('basket'))

    // If there are basket product cards on the page
    if (orders.length) {
      // Loop through each basket product card
      orders.forEach(order => {
        // Find the corresponding basket item in the basket array based on the product id
        const basketItem = basket.find(
          product => product.productId === +order.id
        )

        // If basket item does not exist, skip to the next iteration
        if (!basketItem) return

        // Update the order quantity input with the basket item count
        order.querySelector('.order-quantity').value = basketItem.count

        // Update the items count text based on the basket item count
        order.querySelector('.items-count').innerText = `(${
          basketItem.count
        } item${basketItem.count > 1 ? 's' : ''})`

        // Calculate and display the subtotal price for the basket item
        const subtotalPrice = (basketItem.count * basketItem.productPrice).toFixed(2)
        order.querySelector('.subtotal-price').innerText = subtotalPrice
      })
    }
  },

  // Async function to handle user login process
  async handleLogin (current, e) {
    // Prevent the default form submission behavior (page reload)
    e.preventDefault()

    // Retrieve username and password input values from the current form
    const username = current.querySelector('#username').value
    const password = current.querySelector('#password').value

    try {
      // Send a POST request to the '/login' endpoint with username and password data
      const res = await fetch('/login', {
        method: 'POST',
        body: JSON.stringify({ username, password }),
        headers: {
          'Content-Type': 'application/json'
        }
      })

      // Parse the response data as JSON
      const data = await res.json()

      // If user is authenticated
      if (data && data.isAuthenticated) {
        // Check if there's a localStorage flag to redirect to basket after login
        const goToBasket = window.localStorage.getItem('goToBasket')
        window.localStorage.setItem('isAuthenticated', 1)

        // If goToBasket flag is set, redirect to the basket page with orders query parameter
        if (goToBasket && parseInt(goToBasket)) {
          const orders = window.localStorage.getItem('basket')
          window.location.href = `/basket?orders=${orders || ''}`
          window.localStorage.removeItem('goToBasket')
        } else {
          // Otherwise, redirect to the home page
          window.location.href = '/home'
        }

        // Show the logout link and hide the login link in the navigation menu
        const logoutElement = document.querySelector("[href='/logout']")
        const loginElment = document.querySelector("[href='/login']")
        logoutElement.style.display = 'block'
        loginElment.style.display = 'none'
      }
    } catch (error) {
      // Log any errors that occur during the login process
      console.log(error)
    }
  },

  // Function to check if user is authenticated and redirect to login if accessing basket page
  checkIfAuthenticated () {
    // Retrieve the authentication status from localStorage
    const isAuthenticated = window.localStorage.getItem('isAuthenticated')

    // Check if the current URL includes '/basket' and user is not authenticated
    if (window.location.href.includes('/basket') && !isAuthenticated) {
      // Redirect to the login page
      window.location.href = '/login'
    }
  },

  // Function to handle user logout functionality
  handleLogout () {
    // Retrieve logout and login elements from the DOM
    const logoutElement = document.querySelector("[href='/logout']")
    const loginElement = document.querySelector("[href='/login']")

    // Retrieve the authentication status from localStorage
    const isAuthenticated = window.localStorage.getItem('isAuthenticated')

    // Check if user is authenticated
    if (isAuthenticated && parseInt(isAuthenticated)) {
      // Display the logout link and hide the login link initially
      logoutElement.style.display = 'block'
      loginElement.style.display = 'none'
    }

    // Add event listener to the logout element for click events
    logoutElement.addEventListener('click', e => {
      // Prevent the default click behavior (following the clicked link)
      e.preventDefault()

      // Remove the 'isAuthenticated' flag from localStorage
      window.localStorage.removeItem('isAuthenticated')

      // Redirect to the login page
      window.location.href = '/login'

      // Display the login link and hide the logout link after logout
      loginElement.style.display = 'block'
      logoutElement.style.display = 'none'
    })
  },

  // Function to update the quantity of a product in the basket
  updateBasket (productId, quantity) {
    // Retrieve basket items from localStorage or initialize as an empty array
    const basket = JSON.parse(window.localStorage.getItem('basket')) || []

    // Find the index of the product in the basket array based on productId
    const existingIndex = basket.findIndex(item => item.productId === +productId)

    // Update the quantity of the product in the basket
    basket[existingIndex].count = quantity

    // Update the basket items in localStorage with the updated basket array
    window.localStorage.setItem('basket', JSON.stringify(basket))
  },

  // Function to handle quantity change for a specific product
  handleQuantityChange (current) {
    // Retrieve the productId from the dataset of the current element
    const productId = current.dataset.id

    // Update the basket with the new quantity for the specified productId
    WAD.updateBasket(productId, current.value)

    // Update the displayed order count and subtotal price after basket update
    WAD.showOrderCountAndPrice()
  },

  // Function to remove a product from the basket
  removeFromBasket (productId) {
    // Retrieve basket items from localStorage
    const basket = JSON.parse(window.localStorage.getItem('basket'))

    // Find the index of the product in the basket array based on productId
    const existingIndex = basket.findIndex(item => item.productId === +productId)

    // Remove the product from the basket array at the found index
    basket.splice(existingIndex, 1)

    // Update the basket count displayed on the webpage
    WAD.updateBasketCount(basket.length)

    // Update the basket items in localStorage with the modified basket array
    window.localStorage.setItem('basket', JSON.stringify(basket))
  },

  // Function to handle deletion of a product from the basket
  handleDeleteFromBasket (current) {
    // Retrieve the productId from the dataset of the current element
    const productId = current.dataset.id

    // Remove the product from the basket by productId
    WAD.removeFromBasket(productId)

    // Remove the corresponding basket product card element from the DOM
    document.getElementById(productId).remove()

    // Retrieve basket orders from localStorage
    const orders = window.localStorage.getItem('basket')

    // Get the current URL and remove query parameters to prepare for history update
    const currentURL = window.location.href.split('?')[0]

    // Construct the updated URL with the updated basket orders as query parameter
    const updatedURL = currentURL + `?orders=${orders || ''}`

    // Update the browser history state and URL without reloading the page
    window.history.pushState({ path: updatedURL }, '', updatedURL)
  },

  // Function to navigate to the product page to retrieve product details
  getProductDetails (current) {
    // Retrieve the productId from the id attribute of the current element
    const productId = current.id

    // Redirect to the product details page for the specified productId
    window.location.href = `/plants/${productId}`
  },

  // Function to open the purchase summary modal with order details
  openPurchaseModal () {
    // Retrieve elements related to the purchase summary modal and basket items
    const purchaseSummaryModal = document.getElementById('purchase-summary-modal')
    const purchaseSummary = document.getElementById('purchase-summary')
    const basketItems = document.querySelectorAll('.basket-product-card')

    // Initialize HTML content for the purchase summary modal
    let SummaryHTML = "<h2>Here's your order summary</h2>"

    // Initialize totalPrice for the purchase total sum
    let totalPrice = 0

    // Loop through each basket item to construct summary information
    basketItems.forEach(item => {
      // Construct HTML for each item in the order summary
      const summaryItemHTML = `
      <div class="summary-item">
        ${item.querySelector('img').outerHTML}
        <h5>${item.querySelector('h4').innerText}</h5>
        ${item.querySelector('.product-price').outerHTML}
      </div>`

      // Retrieve subtotal price for the current item and update total price
      const subtotalPrice = +item.querySelector('.subtotal-price').innerText
      totalPrice += subtotalPrice

      // Append the item HTML to the summary content
      SummaryHTML += summaryItemHTML
    })

    // Append total price information to the summary HTML
    SummaryHTML += `<h3 class="total-price">Total Price:<span>&euro;${totalPrice.toFixed(2)}<span></h3>`

    // Insert the summary HTML into the purchase summary element
    purchaseSummary.insertAdjacentHTML('afterbegin', SummaryHTML)

    // Add 'show' class to display the purchase summary modal
    purchaseSummaryModal.classList.add('show')

    // Scroll the purchase summary modal into view
    purchaseSummaryModal.scrollIntoView()

    // Freeze scrolling on the body to prevent background scrolling when modal is open
    WAD.bodyFreezeScroll()
  },

  // Function to close the purchase summary modal
  closePurchaseModal () {
    // Retrieve the purchase summary modal element
    const purchaseSummaryModal = document.getElementById('purchase-summary-modal')

    // Remove the 'show' class to hide the purchase summary modal
    purchaseSummaryModal.classList.remove('show')

    // Unfreeze scrolling on the body to restore normal scrolling behavior
    WAD.bodyUnfreezeScroll()
  },

  // Function to confirm the purchase and update the UI accordingly
  confirmPurchase (current) {
    // Retrieve necessary DOM elements
    const confirmPurchaseContainer = document.getElementById('confirm-purchase')
    const shoppingBasketContainer = document.getElementById('shopping-basket-container')
    const purchaseForm = document.getElementById('confirm-purchase')
    const triggerFormValidation = document.getElementById('trigger-form-validation')

    // Validate the purchase form before proceeding
    if (!purchaseForm.checkValidity()) {
      // Trigger the browser's built-in form validation error display
      triggerFormValidation.click()
      return // Exit the function if form validation fails
    }
    // Show the confirm purchase container and scroll into view smoothly
    confirmPurchaseContainer.classList.add('show')
    confirmPurchaseContainer.scrollIntoView({ block: 'nearest', behavior: 'smooth' })

    // Check the current button text to determine action
    if (current.innerText === 'Confirm purchase') {
      // Hide the confirm purchase container
      confirmPurchaseContainer.classList.remove('show')

      // Create thank you message and countdown elements
      const thankYou = document.createElement('h1')
      const countdown = document.createElement('div')
      thankYou.classList.add('thank-you')
      thankYou.innerText = 'Thank you for shopping with us!'
      countdown.innerText = '3'

      // Clear the shopping basket container and add thank you message
      shoppingBasketContainer.innerHTML = ''
      shoppingBasketContainer.appendChild(thankYou)
      thankYou.appendChild(countdown)

      // Initialize countdown timer
      let counter = 2
      const countdownInterval = setInterval(() => {
        if (counter >= 0) {
          countdown.innerHTML = counter
          counter -= 1
        } else {
          clearInterval(countdownInterval)
          // Redirect to home page after delay
          setTimeout(() => {
            window.location.href = '/home'
          }, 500)
        }
      }, 1000)

      // Close the purchase modal and update button text
      WAD.closePurchaseModal()
      current.innerText = 'Pay'
    } else {
      // Update button text to 'Confirm purchase'
      current.innerText = 'Confirm purchase'
    }
  },

  // Function to freeze scrolling on the body
  bodyFreezeScroll () {
    // Set the body overflow property to 'hidden' to disable scrolling
    document.body.style.overflow = 'hidden'
  },

  // Function to unfreeze scrolling on the body
  bodyUnfreezeScroll () {
    // Reset the body overflow property to empty string to enable scrolling
    document.body.style.overflow = ''
  }
}

// Event listener to execute the WAD initialization function when the window finishes loading
window.addEventListener('load', WAD.init)
