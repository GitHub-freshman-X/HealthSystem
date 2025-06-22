<template>
  <el-card shadow="hover">
    <div class="summary-card">
      <div class="icon" :style="{ backgroundColor: props.data.color }">
        <!-- <i :class="props.data.icon"></i> -->
        <!-- <el-icon :icon="UserFilled" /> -->
      </div>
      <div class="content">
        <div class="value">{{ props.data.value }}{{ props.data.unit }}</div>
        <div class="title">{{ props.data.title }}</div>
      </div>
    </div>
  </el-card>
</template>

<script setup>
// import { UserFilled, Clock, Histogram, ScaleToOriginal } from '@element-plus/icons-vue'

const props = defineProps({
  data: {
    type: Object,
    required: true
  }
})
</script>

<style lang="scss" scoped>
.summary-card {
  display: flex;
  align-items: center;
  gap: 15px;
}
.icon {
  width: 50px;
  height: 50px;
  border-radius: 12px;
  display: grid;
  place-items: center;
  color: white;
  font-size: 24px;
}
</style>