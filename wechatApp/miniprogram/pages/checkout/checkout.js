import { createOrder } from '../../api/index'


Page({

  data: {
    requestId: '',

    cartList: [],

    subtotal: '0.00',

    packingFee: '2.00',

    deliveryFee: '4.00',

    totalPrice: '0.00',

    remark: '',

    submitting: false,

    address: {

      name: '',

      phone: '',

      detail: ''

    }

  },


  onLoad() {
    const pendingCheckout = wx.getStorageSync('pendingCheckout')
    const pendingOrder = wx.getStorageSync('pendingOrder')
  
    // 优先恢复已经提交过、但结果尚未确认的订单
    const cart = pendingOrder
      ? (pendingCheckout?.cartList || wx.getStorageSync('cart') || [])
      : (pendingCheckout?.cartList || [])
  
    if (!pendingCheckout && !pendingOrder) {
      wx.showToast({
        title: '结算信息不存在，请重新结算',
        icon: 'none'
      })
      return
    }
  
    let subtotal = 0
    cart.forEach(item => {
      subtotal += item.dishPrice * item.quantity
    })
  
    const packingFee = 2.00
    const deliveryFee = 4.00
  
    this.setData({
      requestId: pendingOrder?.requestId || pendingCheckout?.requestId,
      cartList: cart,
      subtotal: subtotal.toFixed(2),
      packingFee: packingFee.toFixed(2),
      deliveryFee: deliveryFee.toFixed(2),
      totalPrice: (subtotal + packingFee + deliveryFee).toFixed(2)
    })
  
    // 恢复之前填写的订单信息，避免重试时内容发生变化
    if (pendingOrder) {
      this.setData({
        address: {
          name: pendingOrder.consigneeName,
          phone: pendingOrder.consigneePhone,
          detail: pendingOrder.consigneeAddress
        },
        remark: pendingOrder.remark || ''
      })
    }
  },

  onNameInput(e) {


    this.setData({

      address: {

        ...this.data.address,

        name: e.detail.value

      }

    })


  },


  onPhoneInput(e) {


    this.setData({

      address: {

        ...this.data.address,

        phone: e.detail.value

      }

    })


  },


  onAddressInput(e) {


    this.setData({

      address: {

        ...this.data.address,

        detail: e.detail.value

      }

    })


  },


  onRemarkInput(e) {


    this.setData({

      remark: e.detail.value

    })


  },


  submitOrder() {


    // 防止重复点击
    if (this.data.submitting) {

      return

    }


    const {

      cartList,

      address,

      remark

    } = this.data



    // 校验购物车

    if (cartList.length === 0) {


      wx.showToast({

        title:'购物车是空的',

        icon:'none'

      })

      return

    }



    // 校验姓名

    if (!address.name.trim()) {


      wx.showToast({

        title:'请输入收货人姓名',

        icon:'none'

      })

      return

    }



    // 校验手机号

    if (!address.phone.trim() || !/^1\d{10}$/.test(address.phone)) {


      wx.showToast({

        title:'请输入正确手机号',

        icon:'none'

      })

      return

    }



    // 校验地址

    if (!address.detail.trim()) {


      wx.showToast({

        title:'请输入收货地址',

        icon:'none'

      })

      return

    }



    // 校验通过，锁定提交

    this.data.submitting = true


    this.setData({

      submitting:true

    })



    wx.showLoading({

      title:'提交中...'

    })



    let params = wx.getStorageSync('pendingOrder')

    if (!params) {
      params = {
        requestId: this.data.requestId,
        consigneeName: address.name,
        consigneePhone: address.phone,
        consigneeAddress: address.detail,
        dishes: JSON.stringify(
          cartList.map(item => ({
            dishId: item.id,
            quantity: item.quantity
          }))
        ),
        remark: remark
      }
    
      wx.setStorageSync('pendingOrder', params)
    }



    createOrder(params)

      .then(res => {


        wx.hideLoading()



        this.data.submitting = false


        this.setData({

          submitting:false

        })



        if(res.code === 200){



          wx.showToast({

            title:'下单成功',

            icon:'success'

          })



          wx.removeStorageSync('cart')
          wx.removeStorageSync('pendingCheckout')
          wx.removeStorageSync('pendingOrder')



          setTimeout(()=>{


            wx.navigateBack({

              delta:1

            })


          },500)



        }else{


          wx.showToast({

            title:res.message || '下单失败',

            icon:'none'

          })


        }



      })


      .catch(err=>{


        wx.hideLoading()



        this.data.submitting = false


        this.setData({

          submitting:false

        })



        console.error('订单提交失败：',err)



        wx.showToast({

          title:'提交失败',

          icon:'none'

        })


      })


  }


})