<template>
  <el-card class="page-container">
    <template #header>
      <div class="header">
        <span>运动记录</span>
        <div>
          <el-button type="primary" @click="dialogVisible=true">记录一下</el-button>
        </div>
      </div>
    </template>

    <!-- 筛选区域 -->
    <el-form :model="filterForm" label-width="100px" class="filter-form" size="small">
      <el-row :gutter="24">
        <el-col :span="4">
          <el-form-item label="姓名">
            <el-input v-model="filterForm.realName" placeholder="请输入姓名" />
          </el-form-item>
        </el-col>

        <el-col :span="4">
          <el-form-item label="运动类型">
            <el-select 
            v-model="filterForm.exerciseType" 
            placeholder="请选择类型" 
            clearable 
            style="width: 100%"
            @clear="filterForm.exerciseType = ''"
            >
              <el-option label="跑步" value="跑步" />
              <el-option label="骑行" value="骑行" />
              <el-option label="游泳" value="游泳" />
              <el-option label="跳绳" value="跳绳" />
              <el-option label="其他" value="其他" />
            </el-select>
          </el-form-item>
        </el-col>

        <el-col :span="5">
          <el-form-item label="开始时间">
            <el-date-picker v-model="filterForm.startDate" type="date" placeholder="选择开始日期" style="width: 100%" />
          </el-form-item>
        </el-col>

        <el-col :span="5">
          <el-form-item label="结束时间">
            <el-date-picker v-model="filterForm.endDate" type="date" placeholder="选择结束日期" style="width: 100%" />
          </el-form-item>
        </el-col>

        <el-col :span="4">
          <el-form-item>
            <el-button type="primary" @click="getAllExerciseRecords">查询</el-button>
            <el-button @click="resetFilter">重置</el-button>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>

    <!-- 展示运动记录 -->
    <el-table :data="records" style="width: 100%">
      <el-table-column label="序号" width="100" type="index"></el-table-column>
      <el-table-column label="名字" width="200" prop="realName"></el-table-column>
      <el-table-column label="运动时间" width="300" prop="exerciseDate"></el-table-column>
      <el-table-column label="运动类型" width="200" prop="exerciseType"></el-table-column>
      <el-table-column label="运动时长" width="200" prop="duration"></el-table-column>
      <el-table-column label="消耗卡路里" width="200" prop="calorieBurned"></el-table-column>
      <el-table-column label="步数" width="200" prop="steps"></el-table-column>

      <template #empty>
        <el-empty description="没有数据" />
      </template>

    </el-table>

    <!-- 分页条 -->
    <el-pagination v-model:current-page="pageNum" v-model:page-size="pageSize" :page-sizes="[3, 5, 10, 20]"
      layout="jumper, total, sizes, prev, pager, next" background :total="total" @size-change="onSizeChange"
      @current-change="onCurrentChange" style="margin-top: 20px; justify-content: flex-end" />

    <!-- 弹框，新建运动记录 -->
    <el-dialog v-model="dialogVisible" title="记录运动" width="30%" @close="clearRecordModel">
      <el-form :model="recordModel" :rules="rules" label-width="100px" style="padding-right: 30px">
        <el-form-item label="名字">
          <el-input v-model="recordModel.realName" readonly></el-input>
        </el-form-item>

        <!-- 日期选择器 -->
        <el-form-item label="运动时间" prop="exerciseDate">
          <el-date-picker
            v-model="recordModel.exerciseDate"
            type="date"
            placeholder="选择日期"
            style="width: 100%;"
            format="YYYY-MM-DD"
            value-format="YYYY-MM-DD"
          />
        </el-form-item>

        <!-- 运动类型 -->
        <el-form-item label="运动类型" prop="exerciseType">
          <el-select v-model="recordModel.exerciseType" placeholder="请选择类型" style="width: 100%">
            <el-option label="跑步" value="跑步" />
            <el-option label="骑行" value="骑行" />
            <el-option label="游泳" value="游泳" />
            <el-option label="跳绳" value="跳绳" />
            <el-option label="其他" value="其他" />
          </el-select>
        </el-form-item>
        
        <el-form-item label="运动时长" prop="duration">
          <el-input-number
            v-model="recordModel.duration"
            :min="1"
            :max="1000"
            label="时长"
            style="width: 100%;"
            placeholder="单位：分钟"
          />
        </el-form-item>
        
        <!-- 消耗卡路里 -->
        <el-form-item label="消耗卡路里" prop="calorieBurned">
          <el-input-number
            v-model="recordModel.calorieBurned"
            :min="0"
            :max="10000"
            style="width: 100%;"
            placeholder="单位：千卡"
          />
        </el-form-item>

        <!-- 步数 -->
        <el-form-item label="步数" prop="steps">
          <el-input-number
            v-model="recordModel.steps"
            :min="0"
            :max="100000"
            style="width: 100%;"
            placeholder="步数"
          />
        </el-form-item>
      </el-form>

      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogVisible = false">取消</el-button>
          <el-button type="primary" @click="uploadRecord">确认</el-button>
        </span>
      </template>
    </el-dialog>

  </el-card>
</template>

<script setup>
import { ref } from 'vue';
import dayjs from 'dayjs'
const records = ref([]);

import { useUserInfoStore } from '@/stores/userInfo';
const userInfoStore = useUserInfoStore();

//分页条数据模型
const pageNum = ref(1)//当前页
const total = ref(20)//总条数
const pageSize = ref(10)//每页条数

//当每页条数发生了变化，调用此函数
const onSizeChange = (size) => {
  pageSize.value = size
  getAllExerciseRecords();
}
//当前页码发生变化，调用此函数
const onCurrentChange = (num) => {
  pageNum.value = num
  getAllExerciseRecords();
}

// 进一步对运动记录进行筛选
const filterForm = ref({
  realName: '',
  exerciseType: '',
  startDate: null,
  endDate: null
})

import { getAllExerciseRecordsService } from '@/api/Exercise.js';
const getAllExerciseRecords = async()=>{
  let params = {
    pageNum: pageNum.value,
    pageSize: pageSize.value,
    name: filterForm.value.realName,
    exerciseType: filterForm.value.exerciseType,
  }

  // 两个日期特殊处理一下
  if (filterForm.value.startDate) {
    params.startDate = dayjs(filterForm.value.startDate).format('YYYY-MM-DD')
  }

  if (filterForm.value.endDate) {
    params.endDate = dayjs(filterForm.value.endDate).format('YYYY-MM-DD')
  }

  let result = await getAllExerciseRecordsService(params);
  if(result.data){
      total.value = result.data.total;
      records.value = result.data.items

      // 添加上名字这个属性
      records.value.forEach(item => {
        item.realName = filterForm.value.realName ? filterForm.value.realName : userInfoStore.getUserInfo().realName;
      });
  }
}
getAllExerciseRecords()

const resetFilter = () => {
  filterForm.value = {
    realName: '',
    exerciseType: '',
    startDate: null,
    endDate: null
  }
  getAllExerciseRecords();
}

// 添加新运动记录
const recordModel = ref({
  realName: userInfoStore.getUserInfo().realName,
  exerciseDate: '',
  exerciseType: '',
  duration: null,
  calorieBurned: null,
  steps: null
})

const dialogVisible = ref(false)

const rules = {
  exerciseDate: [
    { required: true, message: '请选择运动时间', trigger: 'blur'}
  ],
  exerciseType: [
    { required: true, message: '请选择运动类型', trigger: 'blur'}
  ],
  duration: [
    { required: true, message: '请输入运动时长', trigger: 'blur'}
  ],
  calorieBurned: [
    { required: true, message: '请输入消耗的卡路里', trigger: 'blur'}
  ],
  steps: [
    { required: true, message: '请当天输入步数', trigger: 'blur'}
  ],
}

// 上传运动记录
import { uploadRecordService } from '@/api/Exercise.js'
import { ElMessage } from 'element-plus';
const uploadRecord = async()=>{
  let result = await uploadRecordService(recordModel.value)
  dialogVisible.value = false
  getAllExerciseRecords()
  ElMessage.success('运动记录上传成功')
}

const clearRecordModel = ()=> {
  recordModel.value = {
    realName: userInfoStore.getUserInfo().realName,
    exerciseDate: '',
    exerciseType: '',
    duration: null,
    calorieBurned: null,
    steps: null
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
</style>
