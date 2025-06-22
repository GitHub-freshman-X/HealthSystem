<template>
  <el-card class="page-container">
    <template #header>
      <div class="header">
        <span>所有营养素</span>
        <div>
          <el-button type="primary" @click="dialogVisible=true; title='添加营养素'; clearNutrient()">添加营养素</el-button>
        </div>
      </div>
    </template>

    <!-- 筛选 -->
    <el-form :model="filterForm" inline>
      <el-form-item label="营养素名称">
        <el-input v-model="filterForm.nutrientName" placeholder="请输入营养素名称" clearable></el-input>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" @click="getAllNutrients">查询</el-button>
      </el-form-item>
    </el-form>

    <!-- 表格展示 -->
    <el-table :data="nutrients" style="width: 100%">
      <el-table-column label="序号" width="100" type="index"></el-table-column>
      <el-table-column label="名字" width="200" prop="name"></el-table-column>
      <el-table-column label="计量单位" width="200" prop="unit"></el-table-column>
      <el-table-column label="功能描述" prop="functionDesc"></el-table-column>
      <el-table-column label="目标功效" prop="targetBenefits"></el-table-column>

      <el-table-column label="操作" width="100">
        <template #default="{ row }">
          <el-button :icon="Edit" circle plain type="primary" @click="showDialog(row); title='编辑营养素'"></el-button>
          <el-button :icon="Delete" circle plain type="danger" @click="deleteNutrient(row)"></el-button>
        </template>
      </el-table-column>

      <template #empty>
        <el-empty description="没有数据" />
      </template>
    </el-table>

    <!-- 添加营养素弹窗 -->
    <el-dialog v-model="dialogVisible" :title="title" width="30%">
      <el-form :model="nutrient" :rules="rules" label-width="100px" style="padding-right: 30px">
        <el-form-item label="营养素名称" prop="name">
          <el-input v-model="nutrient.name" minlength="1" maxlength="10"></el-input>
        </el-form-item>

        <el-form-item label="计量单位" prop="unit">
          <el-input v-model="nutrient.unit" minlength="1" maxlength="15"></el-input>
        </el-form-item>
        
        <el-form-item label="功能描述" prop="functionDesc">
          <el-input v-model="nutrient.functionDesc" minlength="1" maxlength="15"></el-input>
        </el-form-item>
        
        <el-form-item label="目标功效" prop="targetBenefits">
          <el-input v-model="nutrient.targetBenefits" minlength="1" maxlength="15"></el-input>
        </el-form-item>
      </el-form>

      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogVisible = false">取消</el-button>
          <el-button type="primary" @click="title=='添加营养素' ? addNutrient() : updateNutrient()">确认</el-button>
        </span>
      </template>
    </el-dialog>

  </el-card>
</template>

<script setup>
import { Edit, Delete } from '@element-plus/icons-vue'
import { ref } from 'vue';

const filterForm = ref({
  nutrientName: ''
})

const nutrients = ref([]);
const dialogVisible = ref(false)
const title = ref('添加营养素');

import { getAllNutrientsService } from '@/api/Nutrient';
// 获取营养素数据
const getAllNutrients = async()=>{
  let params = {
    nutrientName: filterForm.value.nutrientName
  }
  let result = await getAllNutrientsService(params);
  nutrients.value = result.data;
}
getAllNutrients()

// 营养素数据模型
const nutrient = ref({
  nutrientId: null,
  name: '',
  unit: '',
  functionDesc: '',
  targetBenefits: ''
})

const rules = {
  name: [
    { required: true, message: '请输入营养素名称', trigger: 'blur' },
  ],
  unit: [
    { required: true, message: '请输入计量单位', trigger: 'blur' },
  ],
  functionDesc: [
    { required: true, message: '请输入功能描述', trigger: 'blur' },
  ],
  targetBenefits: [
    { required: true, message: '请输入目标功效', trigger: 'blur' },
  ]
}

const showDialog = (row)=>{
  nutrient.value = { ...row };
  dialogVisible.value = true;
}

import { ElMessage, ElMessageBox } from 'element-plus';
import {
  addNutrientService,
  deleteNutrientService,
  updateNutrientService
} from '@/api/Nutrient.js';

const addNutrient = async()=>{
  let result = await addNutrientService(nutrient.value);
  ElMessage.success('添加成功');
  dialogVisible.value = false;
  getAllNutrients();
}

const updateNutrient = async()=>{
  let result = await updateNutrientService(nutrient.value);
  ElMessage.success('更新成功');
  dialogVisible.value = false;
  getAllNutrients();
}

const deleteNutrient = (row)=>{
  ElMessageBox.confirm(
    '确定要删除该营养素吗？',
    '温馨提示',
    {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning',
    }
  )
    .then(async() => {
      let result = await deleteNutrientService(row.nutrientId);
      ElMessage({type: 'success', message: '删除成功' })

      // 刷新营养素列表
      getAllNutrients()
    })
    .catch(() => {
      ElMessage({
        type: 'info',
        message: '取消删除',
      })
    })
}

const clearNutrient = ()=>{
  nutrient.value = {
    name: '',
    unit: '',
    functionDesc: '',
    targetBenefits: ''
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
</style>
