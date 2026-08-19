import request from '@/utils/request'

// 分页查询
export function getDishPage(params) {
  return request({
    url: '/dish/list',
    method: 'get',
    params
  })
}

// 添加菜品
export function addDish(data) {
  return request({
    url: '/dish/add',
    method: 'post',
    data
  })
}

// 修改菜品
export function updateDish(data) {
  return request({
    url: '/dish/update',
    method: 'put',
    data
  })
}

// 删除菜品
export function deleteDish(id) {
  return request({
    url: `/dish/${id}`,
    method: 'delete'
  })
}

// 切换状态（停售/起售）
export function toggleDishStatus(id, status) {
  return request({
    url: `/dish/${id}/status`,
    method: 'put',
    data: { status }
  })
}