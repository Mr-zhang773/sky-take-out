Page({
  data: {
    cartList: [],
    totalPrice: '0.00'
  },

  onShow() {
    this.loadCart()
  },

  loadCart() {
    const cart = wx.getStorageSync('cart') || []
    let total = 0
    cart.forEach(item => {
      total += item.dishPrice * item.quantity
    })
    this.setData({
      cartList: cart,
      totalPrice: total.toFixed(2)
    })
  },

  increase(e) {
    const id = e.currentTarget.dataset.id
    let cart = wx.getStorageSync('cart') || []
    cart = cart.map(item => {
      if (item.id === id) {
        item.quantity += 1
      }
      return item
    })
    wx.setStorageSync('cart', cart)
    this.loadCart()
  },

  decrease(e) {
    const id = e.currentTarget.dataset.id
    let cart = wx.getStorageSync('cart') || []
    const index = cart.findIndex(item => item.id === id)
    if (cart[index].quantity > 1) {
      cart[index].quantity -= 1
    } else {
      cart.splice(index, 1)
    }
    wx.setStorageSync('cart', cart)
    this.loadCart()
  },

  goShopping() {
    wx.switchTab({ url: '/pages/category/category' })
  },

  checkout() {
    wx.showToast({ title: '功能开发中', icon: 'none' })
  }
})