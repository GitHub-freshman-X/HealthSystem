<template>
  <el-card class="page-container">
    <template #header>
      <div class="header">
        <span>运动统计</span>
      </div>
    </template>

    <!-- 选择开始时间、结束时间 -->
    <el-form :model="filterForm" label-width="100px" class="filter-form" size="small" inline>
      <el-form-item label="开始时间">
        <el-date-picker v-model="filterForm.startDate" type="date" placeholder="选择开始日期" style="width: 100%" />
      </el-form-item>

      <el-form-item label="结束时间">
        <el-date-picker v-model="filterForm.endDate" type="date" placeholder="选择结束日期" style="width: 100%" />
      </el-form-item>

      <el-form-item>
        <el-button type="primary" @click="getSumTimes">查询</el-button>
      </el-form-item>
    </el-form>

    <!-- 展示 -->
    <el-row :span="12">
      <el-col :span="6" class="summary-label">
        <el-icon><Clock /></el-icon>
        总运动时长
      </el-col>
      <el-col :span="6" class="summary-value">
        {{ sumTimes.durationSum }} <span class="unit">分钟</span>
      </el-col>
    </el-row>

    <el-row :span="12">
      <el-col :span="6" class="summary-label">
        <el-icon><Lightning /></el-icon>
        总消耗卡路里
      </el-col>
      <el-col :span="6" class="summary-value">
        {{ sumTimes.calorieBurnedSum }} <span class="unit">千卡</span>
      </el-col>
    </el-row>

    <el-row :span="12">
      <el-col :span="6" class="summary-label">
        <el-icon><Guide /></el-icon>
        总步数
      </el-col>
      <el-col :span="6" class="summary-value">
        {{ sumTimes.stepsSum }} <span class="unit">步</span>
      </el-col>
    </el-row>
  </el-card>
</template>

<script setup>
import { ref } from 'vue';
import dayjs from 'dayjs';
import { Clock, Lightning, Guide } from '@element-plus/icons-vue'

const filterForm = ref({
  startDate: '',
  endDate: '',
});

const sumTimes = ref({
  durationSum: 0,
  calorieBurnedSum: 0,
  stepsSum: 0
});

// 获取数据
import { getSumTimeService } from '@/api/Exercise.js';
const getSumTimes = async () => {
  let params = ref({})
  if(filterForm.value.startDate){
    params.value.startDate = dayjs(filterForm.value.startDate).format('YYYY-MM-DD');
  }
  if(filterForm.value.endDate){
    params.value.endDate = dayjs(filterForm.value.endDate).format('YYYY-MM-DD');
  }
  let result = await getSumTimeService(params.value);
  sumTimes.value = result.data
};
getSumTimes()

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

.summary-card {
  max-width: 500px;
  margin: auto;
  border-radius: 12px;
  font-size: 16px;
  padding: 24px;
}

.summary-label {
  font-weight: bold;
  display: flex;
  align-items: center;
  gap: 8px;
  color: #555;
}

.summary-value {
  font-size: 20px;
  font-weight: 600;
  color: #303133;
}

.unit {
  font-size: 14px;
  color: #999;
  margin-left: 4px;
}
</style>
