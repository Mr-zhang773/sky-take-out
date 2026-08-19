import { createOrder } from '../../api/index'

Page({
  data: {
    cartList: [],
    subtotal: '0.00',
    packingFee: '2.00',
    deliveryFee: '4.00',
    totalPrice: '0.00',
    remark: '',
    address: {
      name: '',
      phone: '',
      detail: ''
    }
  },

  onLoad() {
    const cart = wx.getStorageSync('cart') || []
    let subtotal = 0
    cart.forEach(item => {
      subtotal += item.dishPrice * item.quantity
    })
    const packingFee = 2.00
    const deliveryFee = 4.00
    const total = subtotal + packingFee + deliveryFee
    this.setData({
      cartList: cart,
      subtotal: subtotal.toFixed(2),
      packingFee: packingFee.toFixed(2),
      deliveryFee: deliveryFee.toFixed(2),
      totalPrice: total.toFixed(2)
    })
  },

  onNameInput(e) {
    this.setData({ address: { ...this.data.address, name: e.detail.value } })
  },

  onPhoneInput(e) {
    this.setData({ address: { ...this.data.address, phone: e.detail.value } })
  },

  onAddressInput(e) {
    this.setData({ address: { ...this.data.address, detail: e.detail.value } })
  },

  onRemarkInput(e) {
    this.setData({ remark: e.detail.value })
  },

  submitOrder() {
    const { cartList, address, totalPrice, remark } = this.data
    if (cartList.length === 0) {
      wx.showToast({ title: '购物车是空的', icon: 'none' })
      return
    }
    if (!address.name.trim()) {
      wx.showToast({ title: '请输入收货人姓名', icon: 'none' })
      return
    }
    if (!address.phone.trim() || !/^1\d{10}$/.test(address.phone)) {
      wx.showToast({ title: '请输入正确的手机号', icon: 'none' })
      return
    }
    if (!address.detail.trim()) {
      wx.showToast({ title: '请输入收货地址', icon: 'none' })
      return
    }

    wx.showLoading({ title: '提交中...' })

   // 原来的 params 你删掉，换成这个：
const params = {
  consigneeName: address.name,                // 对应你Order的 consigneeName
  consigneePhone: address.phone,              // 对应你Order的 consigneePhone
  consigneeAddress: address.detail,           // 对应你Order的 consigneeAddress
  dishes: JSON.stringify(                     // 把数组转成JSON字符串
    cartList.map(item => ({
      dishId: item.id,
      dishName: item.dishName,
      quantity: item.quantity,
      price: item.dishPrice
    }))
  ),
  totalPrice: parseFloat(totalPrice),         // 转成数字，而不是字符串
  remark: remark,
  // 注意：userId 和 status 这些字段，你可以在后端默认填，前端不用传
}

    createOrder(params).then(res => {
      wx.hideLoading()
      if (res.code === 200) {
        wx.showToast({ title: '下单成功', icon: 'success' })
        wx.removeStorageSync('cart')
        setTimeout(() => {
          wx.navigateBack({
            delta: 1   // 返回上一页（就是菜品页面）
          })
        }, 500)
      } else {
        wx.showToast({ title: res.message || '下单失败', icon: 'none' })
      }
    }).catch(err => {
      wx.hideLoading()
      console.error('下单失败：', err)
      wx.showToast({ title: '下单失败，请重试', icon: 'none' })
    })
  }
})