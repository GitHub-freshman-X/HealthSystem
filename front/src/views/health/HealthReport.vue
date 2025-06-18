<template>
  <el-card class="page-container">
    <template #header>
      <div class="header">
        <span>个人体检报告</span>
        <!-- 只负责弹窗，弹出新建报告界面 -->
        <el-button type="primary" @click="dialogVisible=true">上传报告</el-button>
      </div>
    </template>

    <!-- 条件查询 -->
    <el-form label-width="100px" size="small" class="filter-form" inline>

      <el-form-item label="时间">
        <el-date-picker v-model="recordDate" type="date" placeholder="请选择要查询的时间" />
      </el-form-item>

      <el-form-item>
        <el-button type="primary" @click="getHealthReports">查询</el-button>
      </el-form-item>
    </el-form>

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

    <!-- 分页条 -->
    <el-pagination v-model:current-page="pageNum" v-model:page-size="pageSize" :page-sizes="[3, 5, 10, 20]"
      layout="jumper, total, sizes, prev, pager, next" background :total="total" @size-change="onSizeChange"
      @current-change="onCurrentChange" style="margin-top: 20px; justify-content: flex-end" />

    <!-- 上传报告 -->
    <el-dialog v-model="dialogVisible" title="上传报告" width="30%" @close="clearReportModel">
      <el-form :model="reportModel" :rules="rules" label-width="100px" style="padding-right: 30px">
        <el-form-item label="姓名">
          <el-input v-model="reportModel.realName" readonly></el-input>
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
import dayjs from 'dayjs'

import { useTokenStore } from '@/stores/token';
const tokenStore = useTokenStore();

const reports = ref([])

//分页条数据模型
const pageNum = ref(1)//当前页
const total = ref(20)//总条数
const pageSize = ref(10)//每页条数

//当每页条数发生了变化，调用此函数
const onSizeChange = (size) => {
  pageSize.value = size
  getHealthReports();
}
//当前页码发生变化，调用此函数
const onCurrentChange = (num) => {
  pageNum.value = num
  getHealthReports();
}

const recordDate = ref('')
// 获取报告数据
import { getHealthReportsService } from '@/api/Health.js';
const getHealthReports = async()=>{
  let params = {
    pageNum: pageNum.value,
    pageSize: pageSize.value,
  }
  if(recordDate.value) {
    params.recordDate = dayjs(recordDate.value).format('YYYY-MM-DD')
  }
  console.log('getHealthReports', params.recordDate)
  let result = await getHealthReportsService(params);
  reports.value = result.data.items
  total.value = result.data.total;

  // 给没一项加上名字属性
  reports.value.forEach(item => {
    item.realName = userInfoStore.getUserInfo().realName;
  })
}
getHealthReports()

const dialogVisible = ref(false)
const reportModel = ref({
  realName: userInfoStore.getUserInfo().realName,
  imgUrl: ''
})

const rules = {
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

.filter-form {
  margin-bottom: 20px;
}
</style>
