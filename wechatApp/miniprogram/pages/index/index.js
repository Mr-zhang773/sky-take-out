import { getCategoryList, getDishList } from '../../api/index'

Page({
  data: {
    banners: [
      { id: 1, image: '/images/banner1.png' },
      { id: 2, image: '/images/banner2.png' },
      { id: 3, image: '/images/banner3.png' }
    ],
    categoryList: [],
    dishList: [],
    searchKeyword: ''
  },

  onLoad() {
    // 检查登录状态
    const token = wx.getStorageSync('token')
    if (!token) {
      wx.redirectTo({ url: '/pages/login/login' })
      return
    }
    this.loadData()
  },

  onShow() {
    // 每次回到页面刷新数据
    this.loadData()
  },

  async loadData() {
    try {
      // 获取分类
      const categoryRes = await getCategoryList()
      if (categoryRes.data) {
        this.setData({
          categoryList: categoryRes.data.slice(0, 10) // 取前10个
        })
      }

      // 获取推荐菜品
      const dishRes = await getDishList({ page: 1, size: 10 })
      if (dishRes.data) {
        this.setData({
          dishList: dishRes.data.records || []
        })
      }
    } catch (err) {
      console.error('加载失败：', err)
    }
  },

  onSearch(e) {
    this.setData({ searchKeyword: e.detail.value })
    // 防抖搜索，可以调接口
  },

  goToCategory(e) {
    const id = e.currentTarget.dataset.id
    wx.switchTab({ url: '/pages/category/category' })
  },

  viewAllDishes() {
    wx.switchTab({ url: '/pages/category/category' })
  },

  addToCart(e) {
    const id = e.currentTarget.dataset.id
    wx.showToast({ title: '已加入购物车', icon: 'success' })
    console.log('加入购物车菜品ID：', id)
    // 实际购物车逻辑后面补
  }
})