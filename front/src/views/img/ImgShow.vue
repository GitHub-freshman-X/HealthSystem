<template>
  <el-image
    class="thumbnail"
    :src="imgUrl || defaultImage"
    :style="{ width: width + 'px', height: 'auto' }"
    fit="contain"
    :preview-src-list="null"
    @click="dialogVisible = true"
    lazy
  >
    <template #error>
      <div class="error-slot">加载失败</div>
    </template>
    <template #placeholder>
      <div class="loading-slot">加载中...</div>
    </template>
  </el-image>

  <el-dialog
    v-model="dialogVisible"
    :append-to-body="true"
    :width="600"
    :show-close="true"
    :close-on-click-modal="true"
    center
    class="image-dialog"
  >
    <div class="image-container">
      <img :src="imgUrl" class="dialog-image" />
    </div>
  </el-dialog>
</template>

<script setup>
import { ref } from 'vue';

const dialogVisible = ref(false);

const props = defineProps({
  imgUrl: {
    type: String,
    default: '',
  },
  width: {
    type: Number,
    default: 200,
  },
});

const defaultImage = '/default.png'; // 可以放一张默认图

</script>

<style lang="scss" scoped>
.thumbnail {
  border-radius: 8px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.15);
  cursor: pointer;
  transition: transform 0.2s;

  &:hover {
    transform: scale(1.03);
  }
}

.loading-slot,
.error-slot {
  display: flex;
  align-items: center;
  justify-content: center;
  height: 100%;
  font-size: 14px;
  color: #999;
}

/* 保证内容区域也是正方形 */
.image-dialog .el-dialog__body {
  padding: 0;
  height: 500px; /* 与 dialogSize 保持一致 */
  display: flex;
  justify-content: center;
  align-items: center;
}

/* 使图片居中显示 */
.image-container {
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
}

/* 限制图片不超过正方形对话框，保持比例 */
.dialog-image {
  max-width: 100%;
  max-height: 100%;
  object-fit: contain;
}

</style>

