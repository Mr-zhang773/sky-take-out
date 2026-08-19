import Vue from 'vue'
import VueRouter from 'vue-router'
import AppCategory from '@/views/AppCategory.vue'
import AppEmployee from '@/views/AppEmployee.vue'
import AppDish from '@/views/AppDish.vue'
import AppOrder from '@/views/AppOrder.vue'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    redirect: '/category'  // 默认跳转到分类管理
  },
  {
    path: '/category',
    name: 'Category',
    component: AppCategory
  },
  {
    path: '/employee',
    name: 'Employee',
    component: AppEmployee
  },
  {
    path: '/dish',
    name: 'Dish',
    component: AppDish
  },
  {
    path : '/order',
    name : 'Order',
    component : AppOrder
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router