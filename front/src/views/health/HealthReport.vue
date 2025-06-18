<template>
  <el-card class="page-container">
    <template #header>
      <div class="header">
        <span>个人体检报告</span>
        <!-- 只负责弹窗，弹出新建报告界面 -->
        <el-button type="primary" @click="dialogVisible=true">上传报告</el-button>
      </div>
    </template>

    <!-- 展示报告 -->
    <el-table :data="reports" style="width: 100%">
      <el-table-column label="序号" width="100" type="index"></el-table-column>
      <el-table-column label="名字" width="200" prop="realName"></el-table-column>
      <el-table-column label="记录时间" width="300" prop="recordTime"></el-table-column>
      <!-- 图片展示 -->
      <el-table-column label="报告" width="800">
        <template #default="{ row }">
          <ImgShow :imgUrl="row.imgUrl" :width="100" />
        </template>
      </el-table-column>

      <template #empty>
        <el-empty description="没有数据" />
      </template>
    </el-table>

    <!-- 上传报告 -->
    <el-dialog v-model="dialogVisible" title="上传报告" width="30%" @close="clearReportModel">
      <el-form :model="reportModel" :rules="rules" label-width="100px" style="padding-right: 30px">
        <el-form-item label="姓名" prop="realName">
          <el-input v-model="reportModel.realName"></el-input>
        </el-form-item>

        <el-form-item label="报告图片" prop="imgUrl">
          <el-upload 
            class="avatar-uploader" 
            :auto-upload="true" 
            :show-file-list="false"
            action="/api/upload"
            name="file"
            :headers = "{ 'Authorization': tokenStore.getToken() }"
            :on-success="uploadSuccess"
          >
            <img v-if="reportModel.imgUrl" :src="reportModel.imgUrl" class="avatar" />
            <el-icon v-else class="avatar-uploader-icon">
              <Plus />
            </el-icon>
          </el-upload>
        </el-form-item>

        <el-form-item>
          <el-button type="success" @click="uploadReport">上传报告</el-button>
        </el-form-item>

      </el-form>
    </el-dialog>

  </el-card>
</template>

<script setup>
import { ref } from 'vue';
import ImgShow from '@/views/img/ImgShow.vue';
import { useUserInfoStore } from '@/stores/userInfo';
const userInfoStore = useUserInfoStore();
import { Plus } from '@element-plus/icons-vue';

import { useTokenStore } from '@/stores/token';
const tokenStore = useTokenStore();

const reports = ref([])

// 获取报告数据
import { getHealthReportsService } from '@/api/Health.js';
const getHealthReports = async()=>{
  let result = await getHealthReportsService();
  reports.value = result.data;

  // 给没一项加上名字属性
  reports.value.forEach(item => {
    item.realName = userInfoStore.getUserInfo().realName;
  })
}
getHealthReports()

const dialogVisible = ref(false)
const reportModel = ref({
  realName: '',
  imgUrl: ''
})

const rules = {
  realName: [{ required: true, message: '请输入姓名', trigger: 'blur' }],
  imgUrl: [{ required: true, message: '请上传报告图片', trigger: 'change' }]
}

import { ElMessage } from 'element-plus';
const uploadSuccess = (result)=>{
  reportModel.value.imgUrl = result.data
}

import { uploadHealthReportService } from '@/api/Health.js';
const uploadReport = async()=>{
  let result = await uploadHealthReportService(reportModel.value);
  ElMessage.success('报告上传成功');
  dialogVisible.value = false;
  clearReportModel(); // 清空表单数据
  getHealthReports(); // 刷新报告列表
}

const clearReportModel = ()=>{
  reportModel.value = {
    realName: '',
    imgUrl: ''
  }
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

/* 抽屉样式 */
.avatar-uploader {
  :deep() {
    .avatar {
      width: 178px;
      height: 178px;
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
      width: 178px;
      height: 178px;
      text-align: center;
    }
  }
}
</style>
