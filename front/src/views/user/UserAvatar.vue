<template>
  <el-card class="page-container">
    <template #header>
      <div class="header">更换头像</div>
    </template>

    <el-row>
      <el-col :span="12">
        <!-- 下面组件会自动发送请求给后端 -->
        <!-- /api/upload 就是localhost:8080/upload -->
        <el-upload
          ref="uploadRef"
          class="avatar-uploader"
          :show-file-list="false"
          :auto-upload="true"
          action="/api/upload" 
          name="file"
          :headers="{ 'Authorization': tokenStore.getToken() }"
          :on-success="uploadSuccess"
          >
          <img v-if="imgUrl" :src="imgUrl" class="avatar" />
          <img v-else :src="avatar" width="278" />
        </el-upload>

        <br />

        <!-- uploadRef就是上面那个el-upload组件 -->
        <el-button type="primary" :icon="Plus" size="large" @click="uploadRef.$el.querySelector('input').click()">
          选择图片
        </el-button>

        <el-button type="success" :icon="Upload" size="large" @click="updateAvatar">
            上传头像
        </el-button>

      </el-col>
    </el-row>
  </el-card>
</template>

<script setup>
import { Plus, Upload } from '@element-plus/icons-vue'
import avatar from '@/assets/default.png'
import { useTokenStore } from '@/stores/token';
const tokenStore = useTokenStore();
import { useUserInfoStore } from '@/stores/userInfo';
const userInfoStore = useUserInfoStore();
import { ref } from 'vue';

const uploadRef = ref(null)

//用户头像地址
const imgUrl = ref(userInfoStore.getUserInfo().avatarUrl)

const uploadSuccess = (result)=>{
  imgUrl.value = result.data
}

import { uploadAvatarService } from '@/api/User';
import { ElMessage } from 'element-plus';
const updateAvatar = async()=>{
  let result = await uploadAvatarService(imgUrl.value);
  userInfoStore.userInfo.avatarUrl = imgUrl.value
  ElMessage.success('头像上传成功');
}

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

.avatar-uploader {
  :deep() {
    .avatar {
      width: 278px;
      height: 278px;
      display: block;
    }

    .el-upload {
      border: 1px dashed var(--el-border-color);
      border-radius: 6px;
      cursor: pointer;
      position: relative;
      overflow: hidden;
      transition: var(--el-transition-duration-fast);
    }

    .el-upload:hover {
      border-color: var(--el-color-primary);
    }

    .el-icon.avatar-uploader-icon {
      font-size: 28px;
      color: #8c939d;
      width: 278px;
      height: 278px;
      text-align: center;
    }
  }
}
</style>
