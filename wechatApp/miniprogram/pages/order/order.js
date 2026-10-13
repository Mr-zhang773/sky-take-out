import { getOrderList } from '../../api/index'

Page({

  data: {
    orderList: []
  },


  onShow() {
    this.loadOrders()
  },


  async loadOrders() {

    try {

      const res = await getOrderList()

      console.log("订单数据:", res)


      if (res.code === 200) {


        this.setData({

          orderList: res.data || []

        })


      }


    } catch (err) {


      console.error("查询订单失败", err)


      wx.showToast({

        title: '订单加载失败',

        icon: 'none'

      })


    }

  },


  // 跳转点餐页面
  goCategory(){

    wx.switchTab({

      url:'/pages/category/category'

    })

  },


  // 当前订单页面
  goOrder(){

    wx.switchTab({

      url:'/pages/order/order'

    })

  }


})