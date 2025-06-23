<template>
  <el-card class="page-container">
    <template header>
      <div class="header">
        <span>健康数据概览</span>
      </div>
    </template>

    <!-- 筛选一个时间 -->
    <el-form :model="filterForm" class="filter-form" size="small" inline>
      <el-form-item label="日期">
        <el-date-picker v-model="filterForm.date" type="date" placeholder="选择日期" style="width: 100%" />
      </el-form-item>

      <el-form-item>
        <el-button type="primary" @click="getNutrientData">查询</el-button>
      </el-form-item>
    </el-form>

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
  </el-card>

</template>

<script setup>
import SummaryCard from '@/views/panel/SummaryCard.vue'
import CalorieChart from '@/views/panel/CalorieChart.vue'
import NutrientRadar from '@/views/panel/NutrientRadar.vue'
import ExerciseTable from '@/views/panel/ExerciseTable.vue'
import MealTabs from '@/views/panel/MealTabs.vue'

import { ref } from 'vue'
import dayjs from 'dayjs'

const filterForm = ref({
  date: ''
})

// 上面组件已经定死了，我只需要获取数据

import { useHealthInfoStore } from '@/stores/healthInfo'
const healthInfoStore = useHealthInfoStore()

// summaryCard
const summaryCards = ref([
  { title: '今日步数', value: null, unit: '步', icon: 'el-icon-walk', color: '#36a3f7' },
  { title: '运动时长', value: null, unit: '分钟', icon: 'el-icon-timer', color: '#34bfa3' },
  { title: '消耗卡路里', value: null, unit: '千卡', icon: 'el-icon-fire', color: '#f4516c' },
  { title: '热量净摄入', value: null, unit: '千卡', icon: 'el-icon-scale', color: '#ffb822' }
])

const calorieData = ref({
  breakfast: 0,
  lunch: 0,
  dinner: 0,
  snack: 0,
  exercise: 0,
  basicMetabolizeRatio: healthInfoStore.getHealthInfo().bmr
})

const nutrientData= ref([])

const exerciseRecords= ref([])

const dietRecords= ref([])

import { getAllExerciseRecordsByDateService } from '@/api/Exercise.js'
const getAllExerciseRecordsByDate = async()=>{
  let params = {}
  if( filterForm.value.date) {
    params.date = dayjs(filterForm.value.date).format('YYYY-MM-DD')
  }else{
    params.date = dayjs().format('YYYY-MM-DD')
  }
  let result = await getAllExerciseRecordsByDateService(params)

  if(result.data && result.data.length > 0) {
    let totalSteps = 0
    let totalDuration = 0
    let totalCalories = 0

    result.data.forEach(record=>{
      totalSteps += record.steps
      totalDuration += record.duration
      totalCalories += record.calorieBurned
    })

    summaryCards.value[0].value = totalSteps
    summaryCards.value[1].value = totalDuration
    summaryCards.value[2].value = totalCalories

    // caloriesData中的运动消耗
    calorieData.value.exercise = totalCalories

    // TODO：净热量差
    summaryCards.value[3].value =(calorieData.value.breakfast + calorieData.value.lunch + calorieData.value.dinner + calorieData.value.snack)
                                 - (totalCalories + calorieData.value.basicMetabolizeRatio)

    // 记录exerciseRecord
    exerciseRecords.value = result.data.map(record => ({
      type: record.exerciseType,
      duration: record.duration,
      calories: record.calorieBurned,
    }))
  }
}

// 摄入的卡路里，摄入与运动作差才有净热量差
import { getDietCaloriesService } from '@/api/Diet.js'
const getDietCalories = async()=>{
  let params = {}
  if( filterForm.value.date) {
    params.dietDate = dayjs(filterForm.value.date).format('YYYY-MM-DD')
  }else{
    params.dietDate = dayjs().format('YYYY-MM-DD')
  }
  let result = await getDietCaloriesService(params)

  if(result.data && result.data.length > 0) {
    result.data.forEach(meal=>{
      if(meal.mealType === '早餐') {
        calorieData.value.breakfast = meal.totalCalories
      } else if(meal.mealType === '午餐') {
        calorieData.value.lunch = meal.totalCalories
      } else if(meal.mealType === '晚餐') {
        calorieData.value.dinner = meal.totalCalories
      } else if(meal.mealType === '加餐') {
        calorieData.value.snack = meal.totalCalories
      }
    })

    dietRecords.value = result.data.map(meal=>{
      const items = meal.foodList.map((food, index) => ({
        name: food,
        quantity: meal.quantityList[index] ?? null,
        calories: meal.caloriesList[index] ?? null,
      }));

      return {
        type: meal.mealType,
        items: items
      }
    })
    dietRecords.value.reverse()
  }
}

// // 营养素分析
import { getNutrientSufficientService } from '@/api/Diet.js'
// 获取各个营养素的标准值
import { useUserInfoStore } from '@/stores/userInfo';
const userInfoStore = useUserInfoStore();
const getNutrientStandard = async()=>{
  let params = {
    gender: userInfoStore.getUserInfo().gender,
    age: healthInfoStore.getHealthInfo().age,
  }
  if(filterForm.value.date) {
    params.dietDate = dayjs(filterForm.value.date).format('YYYY-MM-DD');
  }else {
    params.dietDate = dayjs().format('YYYY-MM-DD');
  }

  let result = await getNutrientSufficientService(params)
  if(result.data && result.data.length > 0) {
    nutrientData.value = result.data.map(item=>{
      return {
        name: item.nutrientName,
        intake: item.intakeAmount,
        recommended: item.recommendAmount
      }
    })
  }
}

const getNutrientData = async()=>{
  // 重置数据
  summaryCards.value[0].value = 0
  summaryCards.value[1].value = 0
  summaryCards.value[2].value = 0
  summaryCards.value[3].value = -calorieData.value.basicMetabolizeRatio

  calorieData.value.breakfast = 0
  calorieData.value.lunch = 0
  calorieData.value.dinner = 0
  calorieData.value.snack = 0
  calorieData.value.exercise = 0

  nutrientData.value = []
  exerciseRecords.value = []
  dietRecords.value = []

  await getDietCalories()
  await getAllExerciseRecordsByDate()
  await getNutrientStandard()
}
getNutrientData()


</script>

<style scoped>
.page-container {
  min-height: 100%;
  box-sizing: border-box;

  .header {
    display: flex;
    align-items: center;
    justify-content: space-between;
  }
}

.filter-form {
  margin-bottom: 20px;
}
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
