<template>
  <el-card class="page-container">
    <template #header>
      <div class="header">
        <span>根据功能查询</span>
      </div>
    </template>

    <!-- 查询条件 -->
    <el-form  :model="filterForm" label-width="100px" class="filter-form" size="small" inline>
      <el-form-item label="功能关键词">
        <el-input v-model="filterForm.functionKeyword" placeholder="请输入功能关键词" />
      </el-form-item>

      <el-form-item>
        <el-button type="primary" @click="getNutrientsByFunction">查询</el-button>
      </el-form-item>
    </el-form>

    <!-- 展示营养素 -->
    <el-table :data="nutrients" style="width: 100%">
      <el-table-column label="序号" type="index" width="100"></el-table-column>
      <el-table-column label="推荐营养素" prop="nutrientName" width="200"></el-table-column>
      <el-table-column label="推荐食物" prop="foodList" ></el-table-column>

      <template #empty>
        <el-empty description="没有数据" />
      </template>
    </el-table>

  </el-card>
</template>

<script setup>

import { ref } from 'vue';

const filterForm = ref({
  functionKeyword: '记忆'
})

const rawData = ref([])
const nutrients = ref([]);

import { getNutrientsByFunctionService } from '@/api/Nutrient.js';
const getNutrientsByFunction = async()=>{
  if(!filterForm.value.functionKeyword){
    nutrients.value = [];
    return;
  }
  let params = {
    functionKeyword: filterForm.value.functionKeyword
  }
  let result = await getNutrientsByFunctionService(params);
  if(result.data){
    rawData.value = result.data
    // console.log(rawData.value)
    nutrients.value = rawData.value.reduce((acc, item)=>{
      console.log(item.nutrientName)
      let group = acc.find(g => g.nutrientName === item.nutrientName)
      if(!group){
        group = {
          nutrientName: item.nutrientName,
          foodList: []
        }
        acc.push(group);
      }
      console.log(group)

      group.foodList.push(item.foodName)

      return acc
    }, []).map(group => ({
      nutrientName: group.nutrientName,
      foodList: group.foodList.join('、')
    }));

    console.log(nutrients.value)
  }
}
getNutrientsByFunction()

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
