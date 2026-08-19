import { getCategoryList, getDishList } from '../../api/index'

Page({
  data: {
    categoryList: [],
    dishList: [],
    currentCategory: null,
    cart: [],
    cartCount: 0,
    cartTotal: '0.00',
    showCartPopup: false,
    scrollIntoView: ''  // 用于滚动到指定分类
  },

  onLoad() {
    this.loadCategory()
  },

  onShow() {
    this.loadCart()
  },

  async loadCategory() {
    try {
      const res = await getCategoryList()
      if (res.data && res.data.length > 0) {
        const list = res.data
        // 为每个分类加载对应的菜品
        for (let item of list) {
          const dishRes = await getDishList({
            page: 1,
            size: 100,
            categoryId: item.id
          })
          item.dishes = dishRes.data?.records || []
        }
        this.setData({
          categoryList: list,
          currentCategory: list[0].id
        })
        // 等待渲染完成后滚动到第一个分类
        this.setData({
          scrollIntoView: 'cat-' + list[0].id
        })
      }
    } catch (err) {
      console.error('加载分类失败：', err)
    }
  },

  // 右侧滚动监听
  onScroll(e) {
    const scrollTop = e.detail.scrollTop
    const query = wx.createSelectorQuery()
    // 获取所有分类标题的位置
    this.data.categoryList.forEach((item, index) => {
      query.select('#cat-' + item.id).boundingClientRect()
    })
    query.exec((res) => {
      // 找到当前在可视区域内的分类
      let currentId = this.data.categoryList[0]?.id
      for (let i = 0; i < res.length; i++) {
        if (res[i] && res[i].top <= 100) {
          currentId = this.data.categoryList[i].id
        }
      }
      if (currentId !== this.data.currentCategory) {
        this.setData({ currentCategory: currentId })
      }
    })
  },

  // 点击左侧分类，右侧滚动到对应位置
  switchCategory(e) {
    const id = e.currentTarget.dataset.id
    this.setData({
      currentCategory: id,
      scrollIntoView: 'cat-' + id
    })
  },

  addToCart(e) {
    const id = e.currentTarget.dataset.id
    // 从所有分类的菜品中查找
    let dish = null
    for (let cat of this.data.categoryList) {
      dish = cat.dishes?.find(item => item.id === id)
      if (dish) break
    }
    console.log('完整dish数据：', dish)  // ← 加这行，看控制台打印
    if (!dish) return

    let cart = wx.getStorageSync('cart') || []
    const exist = cart.find(item => item.id === id)

    if (exist) {
      exist.quantity += 1
    } else {
      cart.push({
        id: dish.id,
        dishName: dish.dishName,
        dishPrice: dish.dishPrice,
        dishImage: dish.dishImage || '/images/default_food.png',
        quantity: 1
      })
    }

    wx.setStorageSync('cart', cart)
    this.loadCart()
    wx.showToast({ title: '已加入', icon: 'success' })
  },

  loadCart() {
    const cart = wx.getStorageSync('cart') || []
    let count = 0
    let total = 0
    cart.forEach(item => {
      count += item.quantity
      total += item.dishPrice * item.quantity
    })
    this.setData({
      cart: cart,
      cartCount: count,
      cartTotal: total.toFixed(2)
    })
  },

  showCartPopup() {
    this.setData({ showCartPopup: true })
  },

  closeCartPopup() {
    this.setData({ showCartPopup: false })
  },

  increaseQty(e) {
    const id = e.currentTarget.dataset.id
    let cart = wx.getStorageSync('cart') || []
    cart = cart.map(item => {
      if (item.id === id) item.quantity += 1
      return item
    })
    wx.setStorageSync('cart', cart)
    this.loadCart()
  },

  decreaseQty(e) {
    const id = e.currentTarget.dataset.id
    let cart = wx.getStorageSync('cart') || []
    const idx = cart.findIndex(item => item.id === id)
    if (idx === -1) return
    if (cart[idx].quantity > 1) {
      cart[idx].quantity -= 1
    } else {
      cart.splice(idx, 1)
    }
    wx.setStorageSync('cart', cart)
    this.loadCart()
  },

  checkout() {
    const cart = wx.getStorageSync('cart') || []
    if (cart.length === 0) {
      wx.showToast({ title: '购物车是空的', icon: 'none' })
      return
    }
    wx.navigateTo({
      url: '/pages/checkout/checkout'
    })
  } })