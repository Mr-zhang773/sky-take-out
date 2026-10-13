import request from '../utils/request'


// ============ 用户相关 ============

export const login = (data) => {

  return request({

    url: '/user/login',

    method: 'POST',

    data

  })

}



// ============ 分类相关 ============

export const getCategoryList = () => {

  return request({

    url: '/user/category/list',

    method: 'GET',

    data: {

      currentPage: 1,

      pageSize: 100

    }

  })

}



// ============ 菜品相关 ============

export const getDishList = (params) => {

  return request({

    url: '/user/dish/list',

    method: 'GET',

    data: {

      currentPage: 1,

      pageSize: 100,

      ...params

    }

  })

}



// ============ 购物车相关 ============

export const addToCart = (data) => {

  return request({

    url: '/cart/add',

    method: 'POST',

    data

  })

}



export const getCartList = () => {

  return request({

    url: '/cart/list',

    method: 'GET'

  })

}



// ============ 订单相关 ============

export const createOrder = (data) => {

  return request({

    url: '/user/order/create',

    method: 'POST',

    data

  })

}



export const getOrderList = () => {

  return request({

    url: '/user/order/list',

    method: 'GET'

  })

}