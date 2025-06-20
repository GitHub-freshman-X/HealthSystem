<template>
  <el-card class="page-container">
    <template #header>
      <div class="header">
        <span>饮食运动日记</span>
        <el-button type="primary" @click="dialogVisible=true">记录一下</el-button>
      </div>
    </template>

    <!-- 筛选区域 -->
    <el-form :model="filterForm" label-width="100px" class="filter-form" size="small" inline>
      <el-form-item label="日记时间">
        <el-date-picker v-model="filterForm.diaryDate" type="date" placeholder="选择日期" style="width: 100%" />
      </el-form-item>

      <el-form-item>
        <el-button type="primary" @click="getDietExerciseDiary">查询</el-button>
      </el-form-item>
    </el-form>

    <!-- 展示区域 -->
    <el-table :data="diarys" style="width: 100%">
      <el-table-column label="序号" width="100" type="index" />
      <el-table-column label="名字" width="200" prop="realName" />
      <el-table-column label="日记时间" width="200" prop="diaryDate" />
      <el-table-column label="饮水" width="200" prop="waterIntake" />
      <el-table-column label="日记内容" prop="content" show-overflow-tooltip />

      <el-table-column label="操作">
        <template #default="scope">
          <el-button size="mini" type="primary" @click="showDiary(scope)">查看</el-button>
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

    <!-- 弹框 -->
    <el-dialog v-model="dialogVisible" title="日记" width="40%" @close="clearDiaryModel">
      <el-form :model="diaryModel" style="padding-right: 30px">
        <el-form-item label="名字">
          <el-input v-model="diaryModel.realName" readonly />
        </el-form-item>

        <!-- 日期选择器 -->
        <el-form-item label="日记时间">
          <el-date-picker
            v-model="diaryModel.diaryDate"
            type="date"
            placeholder="日记时间"
            style="width: 100%;"
          />
        </el-form-item>

        <el-form-item label="饮水">
          <el-input-number
            v-model="diaryModel.waterIntake"
            label="饮水量"
            style="width: 100%;"
            placeholder="单位：ml"
          />
        </el-form-item>

        <el-form-item label="文章内容">
          <div class="editor">
            <quill-editor theme="snow" v-model:content="diaryModel.content" contentType="html" />
          </div>
        </el-form-item>
      </el-form>

      <template #footer>
        <el-button type="primary" @click="uploadDiary">发布</el-button>
      </template>

    </el-dialog>

  </el-card>
</template>

<script setup>
import { ref } from 'vue';
import dayjs from 'dayjs'

const filterForm = ref({
  diaryDate: ''
})

const diarys = ref([])

//分页条数据模型
const pageNum = ref(1)//当前页
const total = ref(20)//总条数
const pageSize = ref(10)//每页条数

//当每页条数发生了变化，调用此函数
const onSizeChange = (size) => {
  pageSize.value = size
  getDietExerciseDiary();
}
//当前页码发生变化，调用此函数
const onCurrentChange = (num) => {
  pageNum.value = num
  getDietExerciseDiary();
}

import { getDietFoodsDiaryService, uploadDietExerciseDiaryService } from '@/api/Diet.js';
import { useUserInfoStore } from '@/stores/userInfo';
const userInfoStore = useUserInfoStore();
const getDietExerciseDiary = async()=>{
  let params = {
    pageNum: pageNum.value,
    pageSize: pageSize.value
  }
  if(filterForm.value.diaryDate){
    params.diaryDate = dayjs(filterForm.value.diaryDate).format('YYYY-MM-DD');
  }

  let result = await getDietFoodsDiaryService(params);
  console.log(result.data)
  // console.log(result.data.total)

  if(result.data){
    total.value = result.data.total;
    diarys.value = result.data.items;

    diarys.value.forEach(item => {
      item.realName = userInfoStore.getUserInfo().realName
    });
  }
}
getDietExerciseDiary()

const showDiary = (scope)=>{
  diaryModel.value = {
    realName: scope.row.realName,
    diaryDate: scope.row.diaryDate,
    waterIntake: scope.row.waterIntake,
    content: scope.row.content
  }
  dialogVisible.value = true;
}

const dialogVisible = ref(false);

const diaryModel = ref({
  realName: userInfoStore.getUserInfo().realName,
  diaryDate: '',
  waterIntake: '',
  content: ''
})

import { QuillEditor } from '@vueup/vue-quill'
import '@vueup/vue-quill/dist/vue-quill.snow.css'

import { ElMessage } from 'element-plus';
const uploadDiary = async()=>{
  if(diaryModel.value.diaryDate){
    diaryModel.value.diaryDate = dayjs(diaryModel.value.diaryDate).format('YYYY-MM-DD');
  } 
  let result = await uploadDietExerciseDiaryService(diaryModel.value)
  ElMessage.success('日记发布成功');
  dialogVisible.value = false;
  getDietExerciseDiary()
}

const clearDiaryModel = ()=>{
  diaryModel.value = {
    realName: userInfoStore.getUserInfo().realName,
    diaryDate: '',
    waterIntake: '',
    content: ''
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

.filter-form {
  margin-bottom: 20px;
}

.editor {
  width: 100%;

  :deep(.ql-editor) {
    min-height: 200px;
  }
}
</style>
