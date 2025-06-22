<template>
  <el-card header="运动记录">
    <el-table :data="data" height="200">
      <!-- <el-table-column prop="time" label="时间" width="100" /> -->
      <el-table-column label="类型">
        <template #default="{row}">
          <el-tag :type="getTagType(row.type)">{{ row.type }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="duration" label="时长(分钟)" />
      <el-table-column prop="calories" label="消耗(千卡)" />
    </el-table>
  </el-card>
</template>

<script setup>

const props = defineProps({
  data: {
    type: Array,
    required: true
  }
})

const getTagType = (type) => {
  const map = { '跑步': 'danger', '骑行': 'primary', '游泳': 'success', '跳绳': 'success' }
  return map[type] || 'info'
}

const intensityColor = (val) => {
  if (val > 80) return '#f56c6c'
  if (val > 50) return '#e6a23c'
  return '#67c23a'
}
</script>