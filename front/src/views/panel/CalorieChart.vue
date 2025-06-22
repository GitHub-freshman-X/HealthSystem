<template>
  <el-card header="热量平衡">
    <div ref="chart" style="height: 300px"></div>
  </el-card>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import * as echarts from 'echarts'

const props = defineProps(['data'])
const chart = ref(null)

onMounted(() => {
  const instance = echarts.init(chart.value)
  instance.setOption({
    tooltip: { trigger: 'axis' },
    legend: { data: ['早餐', '午餐', '晚餐', '加餐', '运动消耗'] },
    grid: { left: '3%', right: '4%', bottom: '3%', containLabel: true },
    xAxis: { type: 'category', data: ['热量分布'] },
    yAxis: { type: 'value', name: '千卡' },
    series: [
      { name: '早餐', type: 'bar', stack: 'diet', data: [props.data.breakfast] },
      { name: '午餐', type: 'bar', stack: 'diet', data: [props.data.lunch] },
      { name: '晚餐', type: 'bar', stack: 'diet', data: [props.data.dinner] },
      { name: '加餐', type: 'bar', stack: 'diet', data: [props.data.snack] },
      { name: '运动消耗', type: 'bar', data: [-props.data.exercise] }
    ]
  })
})
</script>