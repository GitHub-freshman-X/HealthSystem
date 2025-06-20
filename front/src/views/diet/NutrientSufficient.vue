<template>
  <el-card class="page-container">
    <template #header>
      <div class="header">
        <span>营养素摄入是否充足</span>
      </div>
    </template>

    <!-- 筛选 -->
    <el-form :model="filterForm" label-width="100px" class="filter-form" size="small" inline>
      <el-form-item label="日记时间">
        <el-date-picker v-model="filterForm.dietDate" type="date" placeholder="选择日期" style="width: 100%" />
      </el-form-item>

      <el-form-item label="是否怀孕期" style="width: 200px">
        <el-select 
        v-model="filterForm.pregnancy" 
        placeholder="请选择" 
        clearable 
        @clear="filterForm.pregnancy = ''"
        >
          <el-option label="是" value="1" />
          <el-option label="否" value="0" />
        </el-select>
      </el-form-item>

      <el-form-item label="是否哺乳期" style="width: 200px">
        <el-select 
        v-model="filterForm.lactation" 
        placeholder="请选择" 
        clearable 
        @clear="filterForm.lactation = ''"
        >
          <el-option label="是" value="1" />
          <el-option label="否" value="0" />
        </el-select>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" @click="getNutrientStandard">查询</el-button>
      </el-form-item>
    </el-form>

    <!-- 来吧，展示 -->
    <el-table :data="sufficients" style="width: 100%">
      <el-table-column label="序号" width="100" type="index" />
      <el-table-column label="名字" width="200" prop="realName" />
      <el-table-column label="日子" width="200" prop="dietDate" />
      <el-table-column label="营养素" width="200" prop="nutrientName" />
      <el-table-column label="摄入量" width="200" prop="intakeAmount" />
      <el-table-column label="推荐摄入量" width="200" prop="recommendAmount" />
      <el-table-column label="是否充足" width="100" prop="isSufficient" />

      <template #empty>
        <el-empty description="没有数据" />
      </template>
    </el-table>

  </el-card>
</template>

<script setup>

import { ref } from 'vue';
import dayjs from 'dayjs'

const filterForm = ref({
  dietDate: '',
  pregnancy: '0',
  lactation: '0'
})

const sufficients = ref([]);

import { getNutrientSufficientService } from '@/api/Diet.js'
// 获取各个营养素的标准值
import { useUserInfoStore } from '@/stores/userInfo';
const userInfoStore = useUserInfoStore();
const getNutrientStandard = async()=>{
  let params = {
    gender: userInfoStore.getUserInfo().gender,
    age: dayjs().diff(dayjs(userInfoStore.getUserInfo().birthDate), 'year'),
    pregnancy: filterForm.value.pregnancy,
    lactation: filterForm.value.lactation
  }
  if(filterForm.value.dietDate) {
    params.dietDate = dayjs(filterForm.value.dietDate).format('YYYY-MM-DD');
  }else {
    params.dietDate = dayjs().format('YYYY-MM-DD');
  }

  let result = await getNutrientSufficientService(params)
  if(result.data) {
    sufficients.value = result.data
  }
}
getNutrientStandard()


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
