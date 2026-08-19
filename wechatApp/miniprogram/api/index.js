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
// 获取分类列表
export const getCategoryList = () => {
  return request({
    url: '/user/category/list',
    method: 'GET'
  })
}

// ============ 菜品相关 ============
// 获取菜品列表
export const getDishList = (params) => {
  return request({
    url: '/user/dish/list',
    method: 'GET',
    data: params
  })
}

// ============ 购物车相关 ============
// 加入购物车
export const addToCart = (data) => {
  return request({
    url: '/cart/add',
    method: 'POST',
    data
  })
}

// 获取购物车列表
export const getCartList = () => {
  return request({
    url: '/cart/list',
    method: 'GET'
  })
}

// ============ 订单相关 ============
// 创建订单
export const createOrder = (data) => {
  return request({
    url: '/order/create',
    method: 'POST',
    data
  })
}

// 获取订单列表
export const getOrderList = (params) => {
  return request({
    url: '/order/list',
    method: 'GET',
    data: params
  })
}