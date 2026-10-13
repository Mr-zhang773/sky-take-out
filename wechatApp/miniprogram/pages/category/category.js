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
    scrollIntoView: ''
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

      console.log('分类数据:', res)


      const list = res.data.records || res.data || []


      if (list.length > 0) {


        // 查询每个分类下面的菜品
        for (let item of list) {


          const dishRes = await getDishList({

            page: 1,

            size: 100,

            categoryId: item.id

          })


          console.log('菜品数据:', dishRes)


          item.dishes = dishRes.data.records || dishRes.data || []


        }



        this.setData({

          categoryList: list,

          currentCategory: list[0].id

        })


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


    const query = wx.createSelectorQuery()


    this.data.categoryList.forEach(item => {

      query.select('#cat-' + item.id)
        .boundingClientRect()

    })


    query.exec((res)=>{


      let currentId = this.data.categoryList[0]?.id


      for(let i = 0; i < res.length; i++){


        if(res[i] && res[i].top <= 100){

          currentId = this.data.categoryList[i].id

        }

      }


      if(currentId !== this.data.currentCategory){

        this.setData({

          currentCategory: currentId

        })

      }


    })

  },



  // 点击分类
  switchCategory(e){


    const id = e.currentTarget.dataset.id


    this.setData({

      currentCategory:id,

      scrollIntoView:'cat-'+id

    })

  },



  // 加购物车
  addToCart(e){


    const id = e.currentTarget.dataset.id


    let dish = null


    for(let cat of this.data.categoryList){


      dish = cat.dishes?.find(item=>item.id===id)


      if(dish) break

    }


    console.log('加入购物车菜品:',dish)


    if(!dish) return



    let cart = wx.getStorageSync('cart') || []


    const exist = cart.find(item=>item.id===id)



    if(exist){


      exist.quantity += 1


    }else{


      cart.push({

        id:dish.id,

        dishName:dish.dishName,

        dishPrice:dish.dishPrice,

        dishImage:dish.dishImage || '/images/default_food.png',

        quantity:1

      })


    }



    wx.setStorageSync('cart',cart)


    this.loadCart()


    wx.showToast({

      title:'已加入',

      icon:'success'

    })

  },



  loadCart(){


    const cart = wx.getStorageSync('cart') || []


    let count = 0

    let total = 0



    cart.forEach(item=>{


      count += item.quantity


      total += item.dishPrice * item.quantity


    })



    this.setData({

      cart,

      cartCount:count,

      cartTotal:total.toFixed(2)

    })


  },



  showCartPopup(){

    this.setData({

      showCartPopup:true

    })

  },



  closeCartPopup(){

    this.setData({

      showCartPopup:false

    })

  },



  increaseQty(e){


    const id=e.currentTarget.dataset.id


    let cart=wx.getStorageSync('cart') || []


    cart.forEach(item=>{


      if(item.id===id){

        item.quantity++

      }


    })


    wx.setStorageSync('cart',cart)


    this.loadCart()

  },



  decreaseQty(e){


    const id=e.currentTarget.dataset.id


    let cart=wx.getStorageSync('cart') || []


    const index=cart.findIndex(item=>item.id===id)


    if(index===-1)return



    if(cart[index].quantity>1){


      cart[index].quantity--


    }else{


      cart.splice(index,1)


    }



    wx.setStorageSync('cart',cart)


    this.loadCart()


  },



  checkout() {
    const cart = wx.getStorageSync('cart') || []
  
    if (cart.length === 0) {
      wx.showToast({
        title: '购物车是空的',
        icon: 'none'
      })
      return
    }
  
    // 如果已有未确认结果的订单，继续复用原来的请求
    let pendingCheckout = wx.getStorageSync('pendingCheckout')
    const pendingOrder = wx.getStorageSync('pendingOrder')
  
    if (!pendingCheckout && !pendingOrder) {
      const requestId =
        'order_' + Date.now() + '_' +
        Math.random().toString(36).slice(2, 10)
  
      pendingCheckout = {
        requestId,
        cartList: cart
      }
  
      wx.setStorageSync('pendingCheckout', pendingCheckout)
    }
  
    wx.navigateTo({
      url: '/pages/checkout/checkout'
    })
  }

})