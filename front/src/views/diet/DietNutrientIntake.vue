<template>
  <el-card class="page-container">
    <template #header>
      <div class="header">
        <span>营养素摄入记录</span>
      </div>
    </template>

    <!-- 筛选区域 -->
    <el-form :model="filterForm" label-width="100px" class="filter-form" size="small" inline>
      <el-form-item label="日记时间">
        <el-date-picker v-model="filterForm.dietDate" type="date" placeholder="选择日期" style="width: 100%" />
      </el-form-item>

      <el-form-item label="餐次" style="width: 200px">
        <el-select 
        v-model="filterForm.mealType" 
        placeholder="请选择餐次" 
        clearable 
        @clear="filterForm.mealType = ''"
        >
          <el-option label="早餐" value="早餐" />
          <el-option label="午餐" value="午餐" />
          <el-option label="晚餐" value="晚餐" />
          <el-option label="加餐" value="加餐" />
        </el-select>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" @click="getDietNutrientIntake">查询</el-button>
      </el-form-item>
    </el-form>

    <!-- 来吧，展示 -->
    <el-table :data="intakes" style="width: 100%">
      <el-table-column label="序号" width="100" type="index" />
      <el-table-column label="名字" width="200" prop="realName" />
      <el-table-column label="日子" width="200" prop="dietDate" />
      <el-table-column label="餐次" width="200" prop="mealType" />
      <el-table-column label="营养素" width="200" prop="nutrientName" />
      <el-table-column label="摄入量" width="200" prop="intakeAmount" />

      <template #empty>
        <el-empty description="没有数据" />
      </template>
    </el-table>

    <!-- 分页条 -->
    <el-pagination v-model:current-page="pageNum" v-model:page-size="pageSize" :page-sizes="[3, 5, 10, 20]"
      layout="jumper, total, sizes, prev, pager, next" background :total="total" @size-change="onSizeChange"
      @current-change="onCurrentChange" style="margin-top: 20px; justify-content: flex-end" />


  </el-card>
</template>

<script setup>

import { ref } from 'vue';
import dayjs from 'dayjs'

const filterForm = ref({
  dietDate: '',
  mealType: '',
  pregnancy: '0',
  lactation: '0'
})
const intakes = ref([]);

//分页条数据模型
const pageNum = ref(1)//当前页
const total = ref(20)//总条数
const pageSize = ref(10)//每页条数

//当每页条数发生了变化，调用此函数
const onSizeChange = (size) => {
  pageSize.value = size
  getDietNutrientIntake();
}
//当前页码发生变化，调用此函数
const onCurrentChange = (num) => {
  pageNum.value = num
  getDietNutrientIntake();
}

import { getDietNutrientIntakeService } from '@/api/Diet.js'
const getDietNutrientIntake = async()=>{
  let params = {
    pageNum: pageNum.value,
    pageSize: pageSize.value,
    mealType: filterForm.value.mealType
  }

  if(filterForm.value.dietDate){
    params.dietDate = dayjs(filterForm.value.dietDate).format('YYYY-MM-DD')
  }else{
    params.dietDate = dayjs().format('YYYY-MM-DD')
  }

  let result = await getDietNutrientIntakeService(params)
  if(result.data){
    total.value = result.data.total;
    intakes.value = result.data.items;
  }
}
getDietNutrientIntake()

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
