import request from '@/utils/request'

// 分页查询
export function getCategoryPage(params) {
  return request({
    url: '/category/list',
    method: 'get',
    params
  })
}

// 添加分类
export function addCategory(data) {
  return request({
    url: '/category/add',
    method: 'post',
    data
  })
}

// 修改分类
export function updateCategory(data) {
  return request({
    url:'http://localhost:8083/api/category/update',
    method: 'put',
    data
  })
}

// 删除分类
export function deleteCategory(id) {
  return request({
    url: `/category/${id}`,
    method: 'delete'
  })
}

// 切换状态
export function toggleCategoryStatus(id, status) {
  return request({
    url: `/category/${id}/status`,
    method: 'put',
    data: { status }
  })
}
// 获取所有分类（不分页）
export function getCategoryList() {
  return request({
    url: '/category/all',
    method: 'get'
  })
}