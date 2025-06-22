<template>
  <el-card shadow="hover">
    <div class="summary-card">
      <div class="icon" :style="{ backgroundColor: props.data.color }">
        <component :is="iconComponent" class="icon-svg" />
      </div>
      <div class="content">
        <div class="value">{{ props.data.value ?? '-' }}{{ props.data.unit }}</div>
        <div class="title">{{ props.data.title }}</div>
      </div>
    </div>
  </el-card>
</template>

<script setup>
import {
  UserFilled,
  Clock,
  MagicStick, // 替代 Fire
  ScaleToOriginal
} from '@element-plus/icons-vue'

const props = defineProps({
  data: {
    type: Object,
    required: true
  }
})

const iconMap = {
  'el-icon-walk': UserFilled,
  'el-icon-timer': Clock,
  'el-icon-fire': MagicStick, // 没有 Fire，使用 MagicStick 替代
  'el-icon-scale': ScaleToOriginal
}

const iconComponent = iconMap[props.data.icon] || null
</script>

<style scoped>
.summary-card {
  display: flex;
  align-items: center; /* 垂直居中对齐 */
  justify-content: flex-start; /* 默认左对齐，也可以根据需求调整 */
  gap: 12px; /* 图标和内容之间的间距 */

  .icon {
    /* 固定大小，方便布局 */
    width: 40px;
    height: 40px;
    display: flex;
    justify-content: center;
    align-items: center;
    border-radius: 6px; /* 如果你想要圆角背景 */
    color: white; /* 图标颜色，如果需要 */

    .icon-svg {
      width: 24px;
      height: 24px;
    }
  }

  .content {
    flex: 1; /* 占据剩余空间 */
  }
}
</style>
