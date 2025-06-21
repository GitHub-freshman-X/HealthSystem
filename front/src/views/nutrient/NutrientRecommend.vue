<template>
  <el-card class="page-container">
    <template #header>
      <div class="header">
        <span>营养素推荐摄入量</span>
      </div>
    </template>

    <!-- 筛选 -->
    <el-form :model="filterForm" label-width="100px" class="filter-form" size="small" inline>
        
      <el-form-item label="年龄">
        <el-input-number
          v-model="filterForm.age"
          label="年龄"
          style="width: 150px;"
          placeholder="请输入年龄"
        />
      </el-form-item>

      <el-form-item label="性别">
        <el-select v-model="filterForm.gender" placeholder="请选择性别" style="width: 150px">
          <el-option label="男" value="男" />
          <el-option label="女" value="女" />
        </el-select>
      </el-form-item>
        
      <el-form-item label="身高">
        <el-input-number
          v-model="filterForm.height"
          label="身高"
          style="width: 150px;"
          placeholder="请输入身高"
        />
      </el-form-item>
      
      <el-form-item label="体重">
        <el-input-number
          v-model="filterForm.weight"
          label="体重"
          style="width: 150px;"
          placeholder="请输入体重"
        />
      </el-form-item>

      <el-form-item>
        <el-button type="primary" @click="getSupplyRecommend">查询</el-button>
      </el-form-item>
    </el-form>

    <!-- 展示 -->
    <el-table :data="recommends" style="width: 100%">
      <el-table-column label="序号" width="100" type="index" />
      <el-table-column label="营养素" width="200" prop="nutrientName" />
      <el-table-column label="推荐摄入量" width="200" prop="recommendAmount" />

      <template #empty>
        <el-empty description="没有数据" />
      </template>
    </el-table>

  </el-card>
</template>

<script setup>

import { ref } from 'vue';

import { useHealthInfoStore } from '@/stores/healthInfo';
const healthInfoStore = useHealthInfoStore();
const filterForm = ref({
  age: healthInfoStore.getHealthInfo().age,
  gender: healthInfoStore.getHealthInfo().gender,
  height: healthInfoStore.getHealthInfo().height,
  weight: healthInfoStore.getHealthInfo().weight
})
const recommends = ref([]);

import { getSupplyRecommendService } from '@/api/Nutrient.js';
const getSupplyRecommend = async()=>{
  let params = {
    age: filterForm.value.age,
    gender: filterForm.value.gender,
    height: filterForm.value.height,
    weight: filterForm.value.weight
  }
  let result = await getSupplyRecommendService(params);
  if(result.data){
    recommends.value = result.data;
  }
}
getSupplyRecommend()

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
