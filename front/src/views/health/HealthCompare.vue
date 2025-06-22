<template>

  <el-card class="page-container">
    <template #header>
      <div class="header">
        <span>健康数据对比</span>
      </div>
    </template>

    <!-- 筛选 -->
    <el-form :model="filterForm" class="filter-form" size="small" inline>
      <el-form-item label="开始时间">
        <el-date-picker v-model="filterForm.startDate" type="date" placeholder="选择开始日期" style="width: 100%" />
      </el-form-item>

      <el-form-item label="结束时间">
        <el-date-picker v-model="filterForm.endDate" type="date" placeholder="选择结束日期" style="width: 100%" />
      </el-form-item>

      <el-form-item>
        <el-button type="primary" @click="getData">查询</el-button>
      </el-form-item>
    </el-form>

    <div class="block text-center mt-4">
      <el-carousel trigger="click" height="500px">
        <el-carousel-item
          v-for="(opt, index) in chartOptions"
          :key="index"
        >
          <v-chart :option="opt" autoresize style="height: 100%;" />
        </el-carousel-item>
      </el-carousel>
    </div>
  </el-card>

</template>

<script setup>
import VChart from 'vue-echarts'
import dayjs from 'dayjs';
import { ref } from 'vue'

const filterForm = ref({
  startDate: '',
  endDate: ''
});

// 假设这是后端返回的数据
const rawData = ref([])

import { getUserHealthCompareService } from '@/api/Health.js';
const getUserHealthCompare = async()=>{
  let params = {}
  if(filterForm.value.startDate) {
    params.startDate = dayjs(filterForm.value.startDate).format('YYYY-MM-DD');
  }
  if(filterForm.value.endDate) {
    params.endDate = dayjs(filterForm.value.endDate).format('YYYY-MM-DD');
  }
  let result = await getUserHealthCompareService(params);
  if(result.data){
    rawData.value = result.data
  }
  console.log(rawData.value);
}

const recordDate = ref([]);
const indicators = ref([]);
const chartOptions = ref([]);

const convertRawData = () => {
  recordDate.value = rawData.value.find(d => d.healthName === "recordDate")?.values || [];
  indicators.value = rawData.value.filter(d => d.healthName !== "recordDate");

  chartOptions.value = indicators.value
    .map(item => {
      if (!item.values || item.values.length !== recordDate.value.length) return null;
      return {
        title: { text: item.healthName },
        tooltip: { trigger: 'axis' },
        grid: { left: '3%', right: '4%', bottom: '3%', containLabel: true },
        xAxis: {
          type: "category",
          data: recordDate.value
        },
        yAxis: {
          type: "value"
        },
        series: [
          {
            data: item.values,
            type: "line",
            smooth: true,
            areaStyle: {}
          }
        ]
      };
    })
    .filter(Boolean);
};

const getData = async()=>{
  await getUserHealthCompare()
  convertRawData()
}
getData()


</script>

<style scoped>
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

.el-carousel__item:nth-child(2n) {
  background-color: #dbdbdb;
}

.el-carousel__item:nth-child(2n + 1) {
  background-color: #ffffff;
}
</style>