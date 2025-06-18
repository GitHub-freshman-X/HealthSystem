<template>
  <el-card class="page-container">
    <template #header>
      <div class="header">
        <span>健康信息可视化</span>
      </div>
    </template>

    <!-- 健康信息可视化 -->
    <el-row>
      <el-col :span="24">
        <div style="width: 100%; height: 400px;">
          <v-chart :option="option" autoresize />
        </div>

        <div>性别：{{ healthInfoStore.getHealthInfo().gender }}</div>
        <div>年龄：{{ healthInfoStore.getHealthInfo().age }}</div>
      </el-col>
    </el-row>
  </el-card>
</template>

<script setup>
import { ref, computed } from 'vue'
import { use } from 'echarts/core'
import { BarChart } from 'echarts/charts'
import { TitleComponent, TooltipComponent, GridComponent, LegendComponent } from 'echarts/components'
import { CanvasRenderer } from 'echarts/renderers'
import VChart from 'vue-echarts'

use([BarChart, TitleComponent, TooltipComponent, GridComponent, LegendComponent, CanvasRenderer])

import { useHealthInfoStore } from '@/stores/healthInfo'
const healthInfoStore = useHealthInfoStore()
// 横坐标
const categories = ['身高(cm)', '体重(kg)', '体水分(%)', '蛋白质(%)', '脂肪重量(kg)', '肌肉量(kg)', '体脂率(%)', '基础代谢(*100)', '内脏脂肪', 'BMI']

// 获取用户数据
const userData = computed(()=>{
  const healthInfo = healthInfoStore.getHealthInfo()
  return [
    healthInfo.height || 0,
    healthInfo.weight || 0,
    healthInfo.bodyWater || 0,
    healthInfo.protein || 0,
    healthInfo.fatMass || 0,
    healthInfo.muscleMass || 0,
    healthInfo.bodyFatRate || 0,
    healthInfo.bmr != null ? healthInfo.bmr / 100.0 : 0,
    healthInfo.visceralFat || 0,
    healthInfo.bmi || 0
  ]
})
const avgData = ref([])

// 获取平均值数据
import { getAvgHealthInfoService } from '@/api/Health.js'
const getAvgHealthInfo= async()=>{
  let gender = healthInfoStore.getHealthInfo().gender
  let age = healthInfoStore.getHealthInfo().age
  let result = await getAvgHealthInfoService(gender, age)
  console.log(result.data)
  avgData.value = [
    result.data.avgHeight || 0,
    result.data.avgWeight || 0,
    result.data.avgBodyWater || 0,
    result.data.avgProtein || 0,
    result.data.avgFatMass || 0,
    result.data.avgMuscleMass || 0,
    result.data.avgBodyFatRate || 0,
    result.data.avgBmr ? result.data.avgBmr / 100.0 : 0,
    result.data.avgVisceralFat || 0,
    result.data.avgBmi || 0
  ]
  console.log(avgData.value)
}

import { onMounted, onUnmounted } from 'vue'

// 页面加载时调用
onMounted(() => {
  getAvgHealthInfo()
})

// 页面卸载时清除数据
onUnmounted(() => {
  avgData.value = []
})

// 图标的关键
const option = computed(() => ({
  title: {
    text: '用户与同龄平均值对比'
  },
  tooltip: {
    trigger: 'axis'
  },
  legend: {
    data: ['用户', '平均值']
  },
  grid: {
    left: '3%',
    right: '4%',
    bottom: '3%',
    containLabel: true
  },
  xAxis: {
    type: 'category',
    data: categories
  },
  yAxis: {
    type: 'value'
  },
  series: [
    {
      name: '用户',
      type: 'bar',
      data: userData.value,
      itemStyle: {
        color: '#5470C6'
      }
    },
    {
      name: '平均值',
      type: 'bar',
      data: avgData.value,
      itemStyle: {
        color: '#91CC75'
      }
    }
  ]
}))

</script>

<style lang="scss" scoped>
.page-container {
  min-height: 100%;
  box-sizing: border-box;

  .header {
    display: flex;
    align-items: center;
    justify-content: space-between;
  }
}
</style>
