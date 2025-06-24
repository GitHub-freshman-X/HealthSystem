<template>
  <el-card class="page-container">
    <template #header>
      <div class="header">
        <span>家庭成员</span>
      </div>
    </template>


    <!-- 展示 -->
    <el-table :data="familyMembers">
      <el-table-column label="序号" width="100" type="index"></el-table-column>
      <el-table-column label="姓名" width="200" prop="memberUserName"></el-table-column>
      <el-table-column label="关系" width="200" prop="memberUserRole"></el-table-column>
      <el-table-column label="操作">
        <template #default="{ row }">
          <el-button type="primary" @click="switchLogin(row)">登录账号</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 注册新账号 -->
    <el-dialog v-model="dialogVisible" title="添加新成员" style="width: 40%">
      <el-form :model="registerData" size="large" :rules="rules">
        <el-form-item prop="username">
          <el-input :prefix-icon="User" placeholder="请输入用户名" v-model="registerData.username"></el-input>
        </el-form-item>

        <el-form-item prop="password">
          <el-input :prefix-icon="Lock" placeholder="请输入密码" v-model="registerData.password" type="password" ></el-input>
        </el-form-item>

        <el-form-item prop="rePassword">
          <el-input :prefix-icon="Lock" placeholder="请再次输入密码" v-model="registerData.rePassword" type="password" ></el-input>
        </el-form-item>

        <el-form-item prop="memberUserRole">
          <el-input placeholder="请输入家庭关系" v-model="registerData.memberUserRole" ></el-input>
        </el-form-item>

        <el-form-item>
          <el-button type="primary" @click="register">注册</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>

    <el-button type="primary" @click="dialogVisible=true">注册家庭成员</el-button>

  </el-card>
</template>

<script setup>
import { ref } from 'vue';

const familyMembers = ref([]);

import { 
  getFamilyMembersService, 
  switchLoginService, 
  getUserInfoService, 
  registerFamilyMember } from '@/api/User.js'
const getFamilyMembers = async()=>{
  let result = await getFamilyMembersService();
  if(result.data){
    familyMembers.value = result.data;
  }
}
getFamilyMembers()

import { ElMessageBox, ElMessage } from 'element-plus';
import { useRouter } from 'vue-router';
const router = useRouter();
import { useUserInfoStore } from '@/stores/userInfo'
const userInfoStore = useUserInfoStore()
import { useTokenStore } from '@/stores/token.js';
const tokenStore = useTokenStore();
import { useHealthInfoStore } from '@/stores/healthInfo';
const healthInfoStore = useHealthInfoStore();
const switchLogin = (row)=>{
  ElMessageBox.confirm(
    '确定要切换登录账号吗？',
    '温馨提示',
    {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning',
    }
  )
    .then(async() => {
      let result = await switchLoginService(row.memberUserId)
      tokenStore.setToken(result.data)
      let userInfo = await getUserInfoService()
      userInfoStore.setUserInfo(userInfo.data)
      router.push('/user/info')
      healthInfoStore.clearHealthInfo() // 清除健康信息
      ElMessage({type: 'success', message: '登录成功' })
    })
    .catch(() => {
      ElMessage({
        type: 'info',
        message: '取消删除',
      })
    })
}

import { User, Lock } from '@element-plus/icons-vue'
const dialogVisible = ref(false);

const registerData = ref({
  username: '',
  password: '',
  rePassword: '',
  memberUserRole: ''
})

const chkRePassword = (rule, value, callback)=>{
  if(value==''){
    callback(new Error('请再次输入密码'));
  } else if(value !== registerData.value.password) {
    callback(new Error('两次输入的密码不一致'));
  } else {
    callback();
  }
}

const rules = {
  username: [
    { required: true, message: '请输入用户名', trigger: 'blur' },
    { min: 5, max: 16, message: '用户名长度在 5 到 16 个字符之间', trigger: 'blur' }
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { min: 5, max: 16, message: '密码长度在 5 到 16 个字符之间', trigger: 'blur' }
  ],
  rePassword:[
    { validator: chkRePassword, trigger: 'blur' }
  ],
  memberUserRole: [
    { required: true, message: '请输入家庭关系', trigger: 'blur' }
  ]
}

const register = async()=>{
  let result = await registerFamilyMember(registerData)
  dialogVisible.value = false
  getFamilyMembers()
  ElMessage.success('注册成功')
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
