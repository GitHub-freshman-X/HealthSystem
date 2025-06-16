import { createRouter, createWebHistory } from 'vue-router';

// 引入
import Login from '@/views/Login.vue'
import Layout from '@/views/Layout.vue'
import UserInfo from '@/views/user/UserInfo.vue'
import UserAvatar from '@/views/user/UserAvatar.vue'
import UserResetPassword from '@/views/user/UserResetPassword.vue'

// 定义路由关系
const routes = [
  {
    path: '/login',
    component: Login
  },
  {
    path: '/',
    redirect: '/user/info',
    component: Layout,
    children: [
      { path: '/user/info', component: UserInfo },
      { path: '/user/avatar', component: UserAvatar },
      { path: '/user/resetPassword', component: UserResetPassword }
    ]
  }
]

// 定义路由器
const router = createRouter({
  history: createWebHistory(),
  routes: routes
})

// 导出
export default router;
