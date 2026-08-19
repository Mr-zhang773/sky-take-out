import request from '@/utils/request'

// 获取订单列表
export function getOrderList(params) {
  return request({
    url: '/admin/order/list',
    method: 'get',
    params
  })
}

// 更新订单状态
export function updateOrderStatus(id, status) {
  return request({
    url: `/admin/order/status/${id}`,
    method: 'put',
    params: { status }
  })
}