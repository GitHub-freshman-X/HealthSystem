<template>
  <el-container class="layout-container">
    <el-aside width="200px">
      <el-menu
        active-text-color="#ffd04b"
        background-color="#232323"
        text-color="#ffffff"
        unique-opened
        router
      >

        <!-- 管理其他账号 -->
        <el-sub-menu index="0">
          <template #title>
            <el-icon>
              <Management />
            </el-icon>
            <span>账号中心</span>
          </template>

          <!-- 查看并管理所有账号 -->
          <el-menu-item index="/admin/users/manageUsers">
            <span>管理账号</span>
          </el-menu-item>

        </el-sub-menu>

        <!-- 营养素 -->
        <el-sub-menu index="1">
          <template #title>
            <el-icon>
              <Odometer />
            </el-icon>
            <span>营养素</span>
          </template>

          <el-menu-item index="/admin/nutrient/all">
            <span>所有营养素</span>
          </el-menu-item>

          <el-menu-item index="/nutrient/supplyRecommend">
            <span>营养素推荐</span>
          </el-menu-item>

          <el-menu-item index="/nutrient/foodNutrient">
            <span>常见食物所含营养素</span>
          </el-menu-item>

          <el-menu-item index="/nutrient/foodByNutrient">
            <span>包含指定营养素的食物</span>
          </el-menu-item>

          <el-menu-item index="/nutrient/nutrientsByFunction">
            <span>根据功能查询</span>
          </el-menu-item>
        </el-sub-menu>

        <!-- 食物管理 -->
        <el-sub-menu index="2">
          <template #title>
            <el-icon>
              <Food />
            </el-icon>
            <span>食物、饮食管理</span>
          </template>

          <el-menu-item index="/admin/diet/foods">
            <span>食物管理</span>
          </el-menu-item>
        </el-sub-menu>

        <!-- 个人信息管理 -->
        <el-sub-menu index="6">
          <template #title>
            <el-icon>
              <UserFilled />
            </el-icon>
            <span>管理员账号</span>
          </template>

          <el-menu-item index="/admin/user/info">
            <el-icon>
              <User />
            </el-icon>
            <span>基本资料</span>
          </el-menu-item>

          <el-menu-item index="/admin/user/avatar">
            <el-icon>
              <Crop />
            </el-icon>
            <span>更换头像</span>
          </el-menu-item>

          <el-menu-item index="/admin/user/resetPassword">
            <el-icon>
              <EditPen />
            </el-icon>
            <span>重置密码</span>
          </el-menu-item>
        </el-sub-menu>
        
      </el-menu>
    </el-aside>

    <el-container>
      <el-header class="el-header">
        <div><strong>长安健康系统</strong></div>
        
        <el-dropdown placement="bottom-end" @command="handleCommand">
          <span class="el-dropdown__box">
            <el-avatar :src="userInfoStore.getUserInfo().avatarUrl ? userInfoStore.getUserInfo().avatarUrl : avatar" />
            <el-icon>
              <CaretBottom />
            </el-icon>
          </span>

          <!-- 下拉框选项 -->
          <template #dropdown>
            <el-dropdown-item command="info" >基本资料</el-dropdown-item>
            <el-dropdown-item command="avatar" >更换头像</el-dropdown-item>
            <el-dropdown-item command="resetPassword" >重置密码</el-dropdown-item>
            <el-dropdown-item command="logout" >退出登录</el-dropdown-item>
          </template>
        </el-dropdown>
      </el-header>

      <el-main>
        <router-view />
      </el-main>

      <el-footer class="el-footer">长安健康系统 ©2025 Created by XuChangan</el-footer>

    </el-container>
  </el-container>
</template>

<script setup>
import { ref } from 'vue';
import {
  Management,
  Promotion,
  UserFilled,
  User,
  Crop,
  EditPen,
  SwitchButton,
  CaretBottom,
  Odometer,
  Flag,
  Notebook,
  PieChart,
  Food,
} from '@element-plus/icons-vue'
import avatar from '@/assets/default.png'
import { useUserInfoStore } from '@/stores/userInfo.js';
const userInfoStore = useUserInfoStore();
import { useTokenStore } from '@/stores/token.js';
const tokenStore = useTokenStore();
import { useRouter } from 'vue-router';
const router = useRouter();
import { useHealthInfoStore } from '@/stores/healthInfo';
const healthInfoStore = useHealthInfoStore();
import { ElMessage, ElMessageBox } from 'element-plus';

const handleCommand = (command)=>{
  if(command == 'logout'){
    ElMessageBox.confirm(
      '确定要退出登录吗？',
      '温馨提示',
      {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      }
    )
      .then(async () => {
        // 清除用户信息
        userInfoStore.clearUserInfo()
        tokenStore.clearToken()
        healthInfoStore.clearHealthInfo()
        router.push('/login') // 路由到登录页面
        ElMessage.success('退出登录成功');
      })
      .catch(() => {
        ElMessage.info('取消退出登录');
      })
  }else {
    // 路由到对应的用户信息页面
    router.push(`/user/${command}`);
  }
}
</script>

<style lang="scss" scoped>
.layout-container {
  height: 100vh;

  .el-aside {
    background-color: #232323;

    .el-aside__logo {
      height: 120px;
      background: url('@/assets/logo.png') no-repeat center / 120px auto;
    }

    .el-menu {
      border-right: none;
    }
  }

  .el-header {
    background-color: #fff;
    display: flex;
    align-items: center;
    justify-content: space-between;

    .el-dropdown__box {
      display: flex;
      align-items: center;

      .el-icon {
        color: #999;
        margin-left: 10px;
      }

      &:active,
      &:focus {
        outline: none;
      }
    }
  }

  .el-footer {
    display: flex;
    align-items: center;
    justify-content: center;
    font-size: 14px;
    color: #666;
  }
}
</style>
