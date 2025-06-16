import { createRouter, createWebHistory } from 'vue-router';

// 引入
import Login from '@/views/Login.vue'
import Layout from '@/views/Layout.vue'

// 定义路由关系
const routes = [
  {
    path: '/login',
    component: Login
  },
  {
    path: '/',
    redirect: '/login',
    component: Layout
  }
]

// 定义路由器
const router = createRouter({
  history: createWebHistory(),
  routes: routes
})

// 导出
export default router;
