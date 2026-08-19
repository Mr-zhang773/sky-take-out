// src/utils/request.js
import axios from 'axios'

// 创建一个独立的 axios 实例
const service = axios.create({
  baseURL: 'http://localhost:8083/api',          // 基础路径，所有请求都会自动加上 /api
  timeout: 5000             // 超时时间
})

// 请求拦截器（比如：在请求头发送 Token）
service.interceptors.request.use(
  config => {
    // 从 localStorage 获取 token
    const token = localStorage.getItem('token')
    if (token) {
      config.headers['Authorization'] = token
    }
    return config
  },
  error => Promise.reject(error)
)

// 响应拦截器（比如：统一处理错误码）
service.interceptors.response.use(
  response => {
    const res = response.data
    // 如果后端返回的状态码不是 1，说明业务逻辑出错了
    if (res.code !== 200) {
      // 这里可以统一弹出错误提示
      return Promise.reject(new Error(res.msg || 'Error'))
    }
    return res
  },
  error => Promise.reject(error)
)

export default service