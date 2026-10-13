// src/api/employee.js
import request from '@/utils/request'

export function selectPage(params) {
  return request({
    url: '/admin/employee/lists',
    method: 'get',
    params
  })
}
export function nameSelect(params){
  return request({
    url:'/admin/employee/list',
    method:'get',
    params
  })
}
export function updateEmployee(data) {
  return request({
    url: '/admin/employee/update',
    method: 'put',
    data
  })
}
// 添加员工
export function addEmployee(data) {
  return request({
    url: '/admin/employee/add',
    method: 'post',
    data
  })
}