import { createRouter, createWebHistory } from 'vue-router';

// 引入
import Login from '@/views/Login.vue'
import Layout from '@/views/Layout.vue'

// 用户相关
import UserInfo from '@/views/user/UserInfo.vue'
import UserAvatar from '@/views/user/UserAvatar.vue'
import UserResetPassword from '@/views/user/UserResetPassword.vue'
import FamilyMember from '@/views/user/FamilyMember.vue';

// 营养素
import AllNutrient from '@/views/nutrient/AllNutrients.vue'
import NutrientRecommend from '@/views/nutrient/NutrientRecommend.vue';
import FoodNutrient from '@/views/nutrient/FoodNutrient.vue';
import FoodByNutrient from '@/views/nutrient/FoodByNutrient.vue';
import NutrientsByFunction from '@/views/nutrient/NutrientsByFunction.vue';

// 用户健康
import HealthShow from '@/views/health/HealthShow.vue';
import HealthVisualization from '@/views/health/HealthVisualization.vue';
import HealthReport from '@/views/health/HealthReport.vue';
import HealthCompare from '@/views/health/HealthCompare.vue';

// 运动
import ExerciseRecords from '@/views/exercise/ExerciseRecords.vue';
import SumTime from '@/views/exercise/SumTime.vue';

// 饮食
import DietShow from '@/views/diet/DietShow.vue';
import DietExerciseDiary from '@/views/diet/DietExerciseDiary.vue';
import DietNutrientIntake from '@/views/diet/DietNutrientIntake.vue';
import NutrientSufficient from '@/views/diet/NutrientSufficient.vue';

// 运动健康面板
import ExerciseDietPanel from '@/views/panel/ExerciseDietPanel.vue';

// 营养餐推荐
import NutritiousMeal from '@/views/diet/NutritiousMeal.vue';

// 定义路由关系
const routes = [
  {
    path: '/login',
    component: Login
  },
  {
    path: '/',
    redirect: 'health/show',
    component: Layout,
    children: [
      // 用户
      { path: 'user/info', component: UserInfo },
      { path: 'user/avatar', component: UserAvatar },
      { path: 'user/resetPassword', component: UserResetPassword },
      { path: 'user/familyMember', component: FamilyMember },

      // 营养素
      { path: 'nutrient/all', component: AllNutrient },
      { path: 'nutrient/supplyRecommend', component: NutrientRecommend },
      { path: 'nutrient/foodNutrient', component: FoodNutrient },
      { path: 'nutrient/foodByNutrient', component: FoodByNutrient },
      { path: 'nutrient/nutrientsByFunction', component: NutrientsByFunction },

      // 个人健康
      { path: 'health/show', component: HealthShow },
      { path: 'health/visualization', component: HealthVisualization },
      { path: 'health/report', component: HealthReport },
      { path: 'health/compare', component: HealthCompare },

      // 运动记录
      { path: 'exercise/records', component: ExerciseRecords },
      { path: 'exercise/sumTime', component: SumTime },

      // 饮食日记
      { path: 'diet/show', component: DietShow },
      { path: 'diet/diary', component: DietExerciseDiary },
      { path: 'diet/nutrientIntake', component: DietNutrientIntake },
      { path: 'diet/nutrientSufficient', component: NutrientSufficient },

      // 运动健康面板
      { path: 'panel/exerciseDiet', component: ExerciseDietPanel },

      // 营养餐推荐
      { path: 'nutritiousMeal', component: NutritiousMeal }
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
