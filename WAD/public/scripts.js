function updateCartCount(clearCount) {
  const cartCountElement = document.getElementById('cart-items-count')
  const plantElement = document.querySelector('.fa-pagelines')
  let currentCount = parseInt(window.localStorage.getItem('cartCount'))

  if (clearCount) {
    cartCountElement.innerText = ''
    plantElement.classList.remove('show')
    window.localStorage.removeItem('cartCount')
    return
  }

  if (!currentCount) {
    currentCount = 1
    cartCountElement.innerText = currentCount
    window.localStorage.setItem('cartCount', currentCount)
  } else {
    currentCount += 1
    cartCountElement.innerText = currentCount
    window.localStorage.setItem('cartCount', currentCount)
  }

  if (currentCount >= 10) cartCountElement.classList.add('two-digits')
  else cartCountElement.classList.remove('two-digits')

  plantElement.classList.add('show')
}

updateCartCount()
