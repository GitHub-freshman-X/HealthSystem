import { createRouter, createWebHistory } from 'vue-router';

// 引入
import Login from '@/views/Login.vue'
import Layout from '@/views/Layout.vue'

// 用户相关
import UserInfo from '@/views/user/UserInfo.vue'
import UserAvatar from '@/views/user/UserAvatar.vue'
import UserResetPassword from '@/views/user/UserResetPassword.vue'

// 营养素
import AllNutrient from '@/views/nutrient/AllNutrients.vue'

// 用户健康
import HealthShow from '@/views/health/HealthShow.vue';
import HealthVisualization from '@/views/health/HealthVisualization.vue';
import HealthReport from '@/views/health/HealthReport.vue';

// 运动
import ExerciseRecords from '@/views/exercise/ExerciseRecords.vue';

// 定义路由关系
const routes = [
  {
    path: '/login',
    component: Login
  },
  {
    path: '/',
    redirect: 'user/info',
    component: Layout,
    children: [
      // 用户
      { path: 'user/info', component: UserInfo },
      { path: 'user/avatar', component: UserAvatar },
      { path: 'user/resetPassword', component: UserResetPassword },

      // 营养素
      { path: 'nutrient/all', component: AllNutrient },

      // 个人健康
      { path: 'health/show', component: HealthShow },
      { path: 'health/visualization', component: HealthVisualization },
      { path: 'health/report', component: HealthReport },

      // 运动记录
      { path: 'exercise/records', component: ExerciseRecords }
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
