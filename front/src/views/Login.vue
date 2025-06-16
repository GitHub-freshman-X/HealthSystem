<template>
  <el-row class="login-page">
    <el-col :span="12" class="background-pic"></el-col>
    <el-col :span="6" :offset="3" class="form">
      <!-- 登陆界面 -->
      <el-form size="large" autocomplete="true" v-if="isLogin" :model="loginData" :rules="rules">
        <el-form-item>
          <h1>登录</h1>
        </el-form-item>

        <el-form-item prop="username">
          <el-input :prefix-icon="User" placeholder="请输入用户名" v-model="loginData.username"></el-input>
        </el-form-item>

        <el-form-item prop="password">
          <el-input :prefix-icon="Lock" placeholder="请输入密码" v-model="loginData.password" type="password" ></el-input>
        </el-form-item>

        <el-form-item class="flex">
          <div class="flex">
            <el-checkbox>记住我</el-checkbox>
            <el-link type="primary" :underline="false">忘记密码？</el-link>
          </div>
        </el-form-item>

        <el-form-item>
          <el-button class="button" type="primary" @click="login">登录</el-button>
        </el-form-item>

        <el-form-item class="flex">
          <el-link type="info" :underline="false" @click="isLogin = false; clearRegisterData()">
            注册 →
          </el-link>
        </el-form-item>

      </el-form>

      <!-- 注册界面 -->
      <el-form size="large" autocomplete="true" v-else :model="loginData" :rules="rules">
        <el-form-item>
          <h1>注册</h1>
        </el-form-item>

        <el-form-item prop="username">
          <el-input :prefix-icon="User" placeholder="请输入用户名" v-model="loginData.username"></el-input>
        </el-form-item>

        <el-form-item prop="password">
          <el-input :prefix-icon="Lock" placeholder="请输入密码" v-model="loginData.password" type="password" ></el-input>
        </el-form-item>

        <el-form-item prop="rePassword">
          <el-input :prefix-icon="Lock" placeholder="请再次输入密码" v-model="loginData.rePassword" type="password" ></el-input>
        </el-form-item>

        <el-form-item>
          <el-button class="button" type="primary" @click="register">注册</el-button>
        </el-form-item>

        <el-form-item class="flex">
          <el-link type="info" :underline="false" @click="isLogin = true; clearRegisterData()">
            ← 返回
          </el-link>
        </el-form-item>

      </el-form>

    </el-col>
  </el-row>
</template>

<script setup>

import { ref } from 'vue';
import { User, Lock } from '@element-plus/icons-vue'

const isLogin = ref(true); // 控制登录和注册界面

// 数据模型
const loginData = ref({
  username: '',
  password: '',
  rePassword: ''
})

const chkRePassword = (rule, value, callback)=>{
  if(value==''){
    callback(new Error('请再次输入密码'));
  } else if(value !== loginData.value.password) {
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
  ]
}

import { userLoginService, userRegisterService } from '@/api/User.js';
import { ElMessage } from 'element-plus';
// 路由
import { useRouter } from 'vue-router';
const router = useRouter();
// pinia存储token
import { useTokenStore } from '@/stores/token.js';
const tokenStore = useTokenStore();

import { getUserInfoService } from '@/api/User.js'
import { useUserInfoStore } from '@/stores/userInfo'
const userInfoStore = useUserInfoStore()
// 获取用户信息
const getUserInfo = async()=>{
  let result = await getUserInfoService()
  // 存储到pinia中
  userInfoStore.setUserInfo(result.data)
}

const login = async()=>{
  let result = await userLoginService(loginData.value);
  // 登陆错误已经在request.js中处理了
  tokenStore.setToken(result.data)
  getUserInfo();
  ElMessage.success('登录成功');
  router.push('/')
}

const register = ()=>{
  let result = userRegisterService(loginData.value);
  ElMessage.success('注册成功');
  clearRegisterData(); // 清空注册数据
  isLogin.value = true; // 切换回登录界面
}

const clearRegisterData = () => {
  loginData.value.username = '';
  loginData.value.password = '';
  loginData.value.rePassword = '';
}

</script>

<style lang="scss" scoped>
.login-page {
  height: 100vh;
  background-color: #ffffff;

  .background-pic {
    background: url('@/assets/login_bg.jpg') no-repeat center center / cover;
    border-radius: 0 20px 20px 0;
  }

  .form {
    display: flex;
    flex-direction: column;
    justify-content: center;
    user-select: none;

    .title {
      margin: 0 auto;
    }

    .button {
      width: 100%;
    }

    .flex {
      width: 100%;
      display: flex;
      justify-content: space-between;
    }
  }
}
</style>
