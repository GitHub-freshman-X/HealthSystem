<template>
  <el-card header="营养素分析">
    <div ref="radar" :style="{ height: radarHeight }"></div>
  </el-card>
</template>

<script setup>
import { ref, onMounted, watch } from 'vue'
import * as echarts from 'echarts'
const radarHeight = '300px'
const props = defineProps({
  data: {
    type: Array,
    required: true
  }
})

const radar = ref(null)
let chartInstance = null

// 封装一个函数用于设置图表
const updateChart = () => {
  if (!chartInstance) return

  if (!props.data || props.data.length === 0) {
    chartInstance.clear() // 显式清空图表
    return
  }

  chartInstance.setOption({
    tooltip: { trigger: 'item' },
    radar: {
      indicator: props.data.map(item => ({
        name: item.name,
        max: item.recommended * 1.2
      }))
    },
    series: [{
      type: 'radar',
      data: [
        {
          value: props.data.map(item => item.intake),
          name: '实际摄入',
          areaStyle: { color: 'rgba(64, 158, 255, 0.3)' }
        },
        {
          value: props.data.map(item => item.recommended),
          name: '推荐摄入',
          lineStyle: { type: 'dashed' }
        }
      ]
    }]
  })  

  // 重要：重新调整尺寸
  chartInstance.resize()
}

onMounted(() => {
  chartInstance = echarts.init(radar.value)
  updateChart()
})

// 监听 data 变化
watch(() => props.data, updateChart, { deep: true })
</script>
