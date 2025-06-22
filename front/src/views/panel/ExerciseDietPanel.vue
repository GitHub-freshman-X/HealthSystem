<template>
  <div class="health-dashboard">
    <!-- 顶部概览卡片 -->
    <el-row :gutter="20">
      <el-col :span="6" v-for="item in summaryCards" :key="item.title">
        <SummaryCard :data="item" />
      </el-col>
    </el-row>

    <!-- 中部图表区 -->
    <el-row :gutter="20" class="chart-row">
      <el-col :span="12">
        <CalorieChart :data="calorieData" />
      </el-col>
      <el-col :span="12">
        <NutrientRadar :data="nutrientData" />
      </el-col>
    </el-row>

    <!-- 底部详细数据 -->
    <el-row :gutter="20">
      <el-col :span="12">
        <ExerciseTable :data="exerciseRecords" />
      </el-col>
      <el-col :span="12">
        <MealTabs :meals="dietRecords" />
      </el-col>
    </el-row>
  </div>
</template>

<script setup>
import SummaryCard from '@/views/panel/SummaryCard.vue'
import CalorieChart from '@/views/panel/CalorieChart.vue'
import NutrientRadar from '@/views/panel/NutrientRadar.vue'
import ExerciseTable from '@/views/panel/ExerciseTable.vue'
import MealTabs from '@/views/panel/MealTabs.vue'

// import { summaryCards, calorieData, nutrientData, exerciseRecords, dietRecords } from '@/views/panel/data.js'
import { ref } from 'vue'

const calorieData = {
  breakfast: 380,
  lunch: 650,
  dinner: 520,
  snack: 210,
  exercise: 420
}

const nutrientData= [
  { name: '蛋白质', intake: 68, recommended: 60 },
  { name: '脂肪', intake: 42, recommended: 50 },
  { name: '碳水', intake: 210, recommended: 200 },
  { name: '维生素A', intake: 850, recommended: 900 },
  // ...其他13种营养素
]

const exerciseRecords= [
  { time: '07:30', type: '跑步', duration: 30, calories: 240, intensity: 85 },
  { time: '18:00', type: '瑜伽', duration: 35, calories: 180, intensity: 60 }
]

const dietRecords= [
  {
    type: '早餐',
    time: '08:00',
    items: [
      { time: '08:15', name: '全麦面包', calories: 180, protein: 8, carbs: 30, fat: 2 },
      { time: '08:20', name: '牛奶', calories: 120, protein: 8, carbs: 12, fat: 5 }
    ]
  },
  // 午餐、晚餐数据...
]

// 上面组件已经定死了，我只需要获取数据

// summaryCard
const summaryCards = ref([
  { title: '今日步数', value: 8432, unit: '步', icon: 'UserFilled', color: '#36a3f7' },
  { title: '运动时长', value: 65, unit: '分钟', icon: 'Clock', color: '#34bfa3' },
  { title: '消耗卡路里', value: 420, unit: '千卡', icon: 'Histogram', color: '#f4516c' },
  { title: '净热量差', value: -180, unit: '千卡', icon: 'ScaleToOriginal', color: '#ffb822' }
])


</script>

<style scoped>
.health-dashboard {
  padding: 20px;
  max-width: 1400px;
  margin: 0 auto;
}

@media (max-width: 768px) {
  .el-col { width: 100% !important; }
  .chart-row .el-col { margin-bottom: 20px; }
}
</style>
