<template>
  <el-card class="page-container">
    <template #header>
      <div class="header">
        <span>健康信息</span>
      </div>
    </template>

    <!-- 用表格详细展示 -->
    <el-row>
      <el-col :span="12">
        <el-form :model="healthInfo" label-width="120px" size="large" :rules="rules">
          <el-form-item label="真实姓名" prop="realName">
            <el-input v-model="healthInfo.realName" readonly />
          </el-form-item>

          <el-form-item label="性别" prop="gender">
            <el-input v-model="healthInfo.gender" readonly />
          </el-form-item>

          <el-form-item label="年龄" prop="age">
            <el-input v-model="healthInfo.age" readonly />
          </el-form-item>

          <el-form-item label="身高" prop="height">
            <el-input v-model="healthInfo.height" type="number">
              <template #suffix>cm</template>
            </el-input>
          </el-form-item>

          <el-form-item label="体重" prop="weight">
            <el-input v-model="healthInfo.weight" type="number">
              <template #suffix>kg</template>
            </el-input>
          </el-form-item>

          <el-form-item label="体水分" prop="bodyWater">
            <el-input v-model="healthInfo.bodyWater" type="number">
              <template #suffix>%</template>
            </el-input>
          </el-form-item>

          <el-form-item label="蛋白质" prop="protein">
            <el-input v-model="healthInfo.protein" type="number">
              <template #suffix>%</template>
            </el-input>
          </el-form-item>

          <el-form-item label="脂肪重量" prop="fatMass">
            <el-input v-model="healthInfo.fatMass" type="number">
              <template #suffix>kg</template>
            </el-input>
          </el-form-item>

          <el-form-item label="体脂率" prop="bodyFatRate">
            <el-input v-model="healthInfo.bodyFatRate" readonly>
              <template #suffix>%</template>
            </el-input>
          </el-form-item>

          <el-form-item label="肌肉量" prop="muscleMass">
            <el-input v-model="healthInfo.muscleMass" type="number">
              <template #suffix>kg</template>
            </el-input>
          </el-form-item>

          <el-form-item label="基础代谢率" prop="bmr">
            <el-input v-model="healthInfo.bmr" type="number">
              <template #suffix>kcal</template>
            </el-input>
          </el-form-item>

          <el-form-item label="内脏脂肪等级" prop="visceralFat">
            <el-input v-model="healthInfo.visceralFat" type="number" />
          </el-form-item>

          <el-form-item label="BMI" prop="bmi">
            <el-input v-model="healthInfo.bmi" readonly />
          </el-form-item>

          <el-form-item>
            <el-button type="primary" @click="update">提交修改</el-button>
          </el-form-item>
        </el-form>
      </el-col>
    </el-row>
  </el-card>
</template>

<script setup>
import { ref } from 'vue';

const healthInfo = ref({
  username: '',
  recordDate: '',
  realName: '',
  gender: '',
  age: null,
  height: null,
  weight: null,
  bodyWater: null,
  protein: null,
  fatMass: null,
  muscleMass: null,
  bmr: null,
  visceralFat: null,
  bodyFatRate: null,
  bmi: null
})

// 校验规则
const rules = {
  username: [
    { required: true, message: '请输入用户名', trigger: 'blur' }
  ],
  recordDate: [
    { required: true, message: '请选择记录日期', trigger: 'change' }
  ],
  realName: [
    { required: true, message: '请输入姓名', trigger: 'blur'}
  ],
  gender: [
    { required: true, message: '请选择性别', trigger: 'change' }
  ],
  age: [
    { required: true, message: '请输入年龄', trigger: 'blur' }
  ],
  height: [
    { required: true, message: '请输入身高', trigger: 'blur' }
  ],
  weight: [
    { required: true, message: '请输入体重', trigger: 'blur' }
  ],
  bodyWater: [
    { required: true, message: '请输入体水分', trigger: 'blur' }
  ],
  protein: [
    { required: true, message: '请输入蛋白质', trigger: 'blur' }
  ],
  fatMass: [
    { required: true, message: '请输入脂肪量', trigger: 'blur' }
  ],
  muscleMass: [
    { required: true, message: '请输入肌肉量', trigger: 'blur' }
  ],
  bmr: [
    { required: true, message: '请输入基础代谢率', trigger: 'blur' }
  ],
  visceralFat: [
    { required: true, message: '请输入内脏脂肪等级', trigger: 'blur' }
  ],
  bodyFatRate: [
    { required: true, message: '请输入体脂率', trigger: 'blur' }
  ],
  bmi: [
    { required: true, message: '请输入BMI', trigger: 'blur' }
  ]
}

import { getHealthInfoService, updateHealthInfoService } from '@/api/Health.js';
import { ElMessage } from 'element-plus';
import { useHealthInfoStore } from '@/stores/healthInfo';
const healthInfoStore = useHealthInfoStore();
// 获取健康信息
const getHealthInfo = async()=>{
  let result = await getHealthInfoService();
  healthInfo.value = result.data;
  healthInfoStore.setHealthInfo(result.data);
}
getHealthInfo()

// 更新健康信息
const update = async()=>{
  let result = await updateHealthInfoService(healthInfo.value)
  ElMessage.success('健康信息更新成功');
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
