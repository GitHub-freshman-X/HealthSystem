<template>
  <el-card class="page-container">
    <template #header>
      <div class="header">
        <span>推荐营养餐</span>
      </div>
    </template>

    <el-table :data="nutritiousMealList">
      <el-table-column label="序号" width="100" type="index" />
      <el-table-column label="推荐营养素" width="200" prop="nutrientName" />
      <el-table-column label="推荐食物" width="200" prop="foodName" />
      <el-table-column
        label="推荐量"
        width="200"
        prop="recAmount"
        :formatter="formatRecAmount"
      />
    </el-table>

  </el-card>
</template>

<script setup>
import { ref } from 'vue';

const nutritiousMealList = ref([])

import { getNutritiousMealService } from '@/api/Diet.js';
import { useHealthInfoStore } from '@/stores/healthInfo';
const healthInfoStore = useHealthInfoStore();
const getNutriousMeal = async()=>{
  let params = {
    gender: healthInfoStore.getHealthInfo().gender,
    age: healthInfoStore.getHealthInfo().age,
  }
  let result = await getNutritiousMealService(params);
  if(result.data && result.data.length>0){
    nutritiousMealList.value = result.data;
  }
}
getNutriousMeal()

const formatRecAmount = (row) => {
  const value = row.recAmount
  if (value == null || isNaN(value)) return '-'
  return `${value.toFixed(2)} g`
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
