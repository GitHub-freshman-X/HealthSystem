<template>
  <el-card class="page-container">
    <template #header>
      <div class="header">
        <span>密码重置</span>
      </div>
    </template>

    <el-form :model="resetPasswordModel" :rules="rules">
      <el-form-item label="旧密码" prop="oldPassword">
        <el-input v-model="resetPasswordModel.oldPassword" type="password" placeholder="请输入旧的密码" />
      </el-form-item>
      
      <el-form-item label="新密码" prop="newPassword">
        <el-input v-model="resetPasswordModel.newPassword" type="password" placeholder="请输入新的密码" />
      </el-form-item>
      
      <el-form-item label="确认密码" prop="confirmPassword">
        <el-input v-model="resetPasswordModel.confirmPassword" type="password" placeholder="请再次输入新的密码" />
      </el-form-item>

      <el-form-item>
        <el-button type="primary" @click="updatePasswrod">提交修改</el-button>
      </el-form-item>
    </el-form>

  </el-card>
</template>

<script setup>
import { ref } from 'vue';

const resetPasswordModel = ref({
  oldPassword: '',
  newPassword: '',
  confirmPassword: ''
});

const chkRePassword = (rule, value, callback)=>{
  console.log(resetPasswordModel.value.newPassword, value)
  if(value==''){
    callback(new Error('请再次输入密码'));
  } else if(value !== resetPasswordModel.value.newPassword) {
    callback(new Error('两次输入的密码不一致'));
  } else {
    callback();
  }
}

const rules = {
  oldPassword: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { min: 5, max: 16, message: '密码长度在 5 到 16 个字符之间', trigger: 'blur' }
  ],
  newPassword: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { min: 5, max: 16, message: '密码长度在 5 到 16 个字符之间', trigger: 'blur' }
  ],
  confirmPassword:[
    { validator: chkRePassword, trigger: 'blur' }
  ]
}

import { updatePasswordService } from '@/api/User.js';
import { useRouter } from 'vue-router';
const router = useRouter();
import { ElMessage } from 'element-plus';
const updatePasswrod = async()=>{
  let result = await updatePasswordService(resetPasswordModel.value.oldPassword, resetPasswordModel.value.newPassword);
  if(result.data){
    if(result.data === '旧密码错误'){
      ElMessage.error('旧密码错误，请重新输入');
      clearResetPasswordModel()
    } else {
      ElMessage.success('密码修改成功，请重新登录');
      clearResetPasswordModel()
      // 这里可以添加跳转到登录页面的逻辑
      router.push('/login');
    }
  }
}

const clearResetPasswordModel = ()=>{
  resetPasswordModel.value = {
    oldPassword: '',
    newPassword: '',
    confirmPassword: ''
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

.filter-form {
  margin-bottom: 20px;
}
</style>
