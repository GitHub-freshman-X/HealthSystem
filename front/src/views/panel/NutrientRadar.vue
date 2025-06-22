<template>
  <el-card header="营养素分析">
    <div ref="radar" style="height: 300px"></div>
  </el-card>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import * as echarts from 'echarts'

const props = defineProps(['data'])
const radar = ref(null)

onMounted(() => {
  const instance = echarts.init(radar.value)
  instance.setOption({
    tooltip: { trigger: 'item' },
    radar: {
      indicator: props.data.map(item => ({
        name: item.name,
        max: item.recommended * 1.2 // 最大显示值
      }))
    },
    series: [{
      type: 'radar',
      data: [{
        value: props.data.map(item => item.intake),
        name: '实际摄入',
        areaStyle: { color: 'rgba(64, 158, 255, 0.3)' }
      }, {
        value: props.data.map(item => item.recommended),
        name: '推荐摄入',
        lineStyle: { type: 'dashed' }
      }]
    }]
  })
})
</script>