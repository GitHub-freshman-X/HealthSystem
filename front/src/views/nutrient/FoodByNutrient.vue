<template>
  <el-card class="page-container">
    <template #header>
      <div class="header">
        <span>常见食物所含营养素</span>
      </div>
    </template>

    <!-- 筛选 -->
    <el-form :model="filterForm" label-width="100px" class="filter-form" size="small" inline>

      <el-form-item label="营养素">
        <el-select v-model="filterForm.nutrientName" 
          placeholder="请选择营养素" 
          style="width: 150px" 
          clearable 
          @clear="filterForm.nutrientName = ''"
        >
          <div v-for="nutrient in nutrients" :key="nutrientId">
            <el-option :label="nutrient.name" :value="nutrient.name"></el-option>
          </div>
        </el-select>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" @click="getFoodByNutrient">查询</el-button>
      </el-form-item>
    </el-form>

    <!-- 展示 -->
    <el-table :data="foodByNutrient" style="width: 100%">
      <el-table-column label="序号" width="100" type="index" />
      <el-table-column label="营养素" width="200" prop="nutrientName" />
      <el-table-column label="食物" width="200" prop="foodName" />
      <el-table-column label="每100g含量" width="200" prop="amount" />

      <template #empty>
        <el-empty description="没有数据" />
      </template>
    </el-table>

  </el-card>
</template>

<script setup>

import { ref } from 'vue';
const filterForm = ref({
  nutrientName: ''
})
const foodByNutrient = ref([]);

import { getFoodByNutrientService } from '@/api/Nutrient.js';
const getFoodByNutrient = async()=>{
  let params = {
    nutrientName: filterForm.value.nutrientName
  }
  let result = await getFoodByNutrientService(params);
  if(result.data){
    foodByNutrient.value = result.data;
  }
}
getFoodByNutrient()

const nutrients = ref([]);

import { getAllNutrientsService } from '@/api/Nutrient';
// 获取营养素数据
const getAllNutrients = async()=>{
  let result = await getAllNutrientsService();
  nutrients.value = result.data;
}
getAllNutrients()

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
