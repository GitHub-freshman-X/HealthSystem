<template>
  <el-card header="热量平衡">
    <div ref="chart" style="height: 300px"></div>
  </el-card>
</template>

<script setup>
import { ref, onMounted, watch } from 'vue'
import * as echarts from 'echarts'

const props = defineProps({
  data: {
    type: Object,
    required: true
  }
})

const chart = ref(null)
let chartInstance = null

onMounted(() => {
  chartInstance = echarts.init(chart.value)
  updateChart()  // 初始渲染
})

watch(() => props.data, () => {
  updateChart()
}, { deep: true })

function updateChart() {
  const data = {
    breakfast: props.data.breakfast ?? 0,
    lunch: props.data.lunch ?? 0,
    dinner: props.data.dinner ?? 0,
    snack: props.data.snack ?? 0,
    exercise: props.data.exercise ?? 0
  }

  chartInstance?.setOption({
    tooltip: { trigger: 'axis' },
    legend: { data: ['早餐', '午餐', '晚餐', '加餐', '运动消耗', '基础代谢'] },
    grid: { left: '3%', right: '4%', bottom: '3%', containLabel: true },
    xAxis: { type: 'category', data: ['热量分布'] },
    yAxis: { type: 'value', name: '千卡' },
    series: [
      { name: '早餐', type: 'bar', stack: 'diet', data: [data.breakfast] },
      { name: '午餐', type: 'bar', stack: 'diet', data: [data.lunch] },
      { name: '晚餐', type: 'bar', stack: 'diet', data: [data.dinner] },
      { name: '加餐', type: 'bar', stack: 'diet', data: [data.snack] },
      { name: '运动消耗', type: 'bar', stack: 'consume', data: [-data.exercise] } ,
      { name: '基础代谢', type: 'bar', stack: 'consume', data: [-props.data.basicMetabolizeRatio] }
    ]
  })
}
</script>
