<template>
  <el-card>
    <template #header>
      <div class="header">
        <span>个人信息</span>
      </div>
    </template>

    <el-row>
      <el-col :span="12">
        <el-form :model="userInfo" :rules="rules" label-width="100px" size="large">
          <!-- 用户名（不可修改） -->
          <el-form-item label="用户名">
            <el-input v-model="userInfo.username" disabled />
          </el-form-item>

          <!-- 姓名 -->
          <el-form-item label="姓名" prop="realName">
            <el-input v-model="userInfo.realName" placeholder="请输入真实姓名" />
          </el-form-item>

          <!-- 性别 -->
          <el-form-item label="性别" prop="gender">
            <el-select v-model="userInfo.gender" placeholder="请选择性别">
              <el-option label="男" value="男" />
              <el-option label="女" value="女" />
            </el-select>
          </el-form-item>

          <!-- 出生日期 -->
          <el-form-item label="出生日期" prop="birthDate">
            <el-date-picker
              v-model="userInfo.birthDate"
              type="date"
              placeholder="选择出生日期"
              format="YYYY-MM-DD"
              value-format="YYYY-MM-DD"
            />
          </el-form-item>

          <!-- 身高 -->
          <el-form-item label="身高 (cm)" prop="height">
            <el-input v-model="userInfo.height" placeholder="请输入身高" />
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

const userInfo = ref({
  username: '',
  realName: '',
  gender: '',
  birthDate: '',
  height: ''
})

// 校验表单
const rules = {
  realName: [{ required: true, message: '请输入姓名', trigger: 'blur' }],
  gender: [{ required: true, message: '请选择性别', trigger: 'change' }],
  birthDate: [{ required: true, message: '请选择出生日期', trigger: 'change' }],
  height: [
    { required: true, message: '请输入身高', trigger: 'blur' },
    { pattern: /^\d+$/, message: '身高必须为数字', trigger: 'blur' }
  ]
}

import { useUserInfoStore } from '@/stores/userInfo.js';
const userInfoStore = useUserInfoStore();
const showUserInfo = ()=>{
  userInfo.value = userInfoStore.getUserInfo();
}
showUserInfo();

// 更新用户信息
import { updateUserInfoService } from '@/api/User.js';
import { ElMessage } from 'element-plus';
import { useHealthInfoStore } from '@/stores/healthInfo';
const healthInfoStore = useHealthInfoStore();
const update = async()=>{
  let result = await updateUserInfoService(userInfo.value);
  ElMessage.success('用户信息更新成功');
  userInfoStore.setUserInfo(userInfo.value);
  healthInfoStore.healthInfo.gender = userInfo.value.gender;
  healthInfoStore.healthInfo.age = new Date().getFullYear() - new Date(userInfo.value.birthDate).getFullYear();
  healthInfoStore.healthInfo.height = userInfo.value.height;
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
