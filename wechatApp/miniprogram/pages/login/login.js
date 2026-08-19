// pages/login/login.js
const app = getApp()

Page({
  data: {
    loading: false
  },

  // 点击登录按钮
  handleLogin() {
    if (this.data.loading) return
    
    this.setData({ loading: true })
    
    // 1. 调用 wx.login 获取 code
    wx.login({
      success: (res) => {
        console.log('wx.login 成功，code:', res.code)
        // 2. 把 code 发送到后端
        this.doLogin(res.code)
      },
      fail: (err) => {
        console.error('wx.login 失败:', err)
        wx.showToast({
          title: '获取登录凭证失败',
          icon: 'none'
        })
        this.setData({ loading: false })
      }
    })
  },

  // 发送 code 到后端
  doLogin(code) {
    wx.request({
      url: 'http://localhost:8083/api/user/login',  // 换成你后端的登录接口
      method: 'POST',
      data: { code: code },
      header: {
        'Content-Type': 'application/json'
      },
      success: (res) => {
        console.log('登录响应:', res)
        
        if (res.statusCode === 200 && res.data.code === 200) {
          const token = res.data.data
          // 3. 存储 token
          wx.setStorageSync('token', token)
          
          wx.showToast({
            title: '登录成功',
            icon: 'success'
          })
          
          // 4. 跳转到首页
          setTimeout(() => {
            wx.reLaunch({
              url: '/pages/category/category'
            })
          }, 500)
          
        } else {
          wx.showToast({
            title: res.data.message || '登录失败',
            icon: 'none'
          })
        }
      },
      fail: (err) => {
        console.error('登录请求失败:', err)
        wx.showToast({
          title: '网络请求失败',
          icon: 'none'
        })
      },
      complete: () => {
        this.setData({ loading: false })
      }
    })
  }
})