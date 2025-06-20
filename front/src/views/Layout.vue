<!-- 大致分成左边菜单栏，右边展示内容 -->
<template>
  <el-container class="layout-container">
    <!-- 左侧菜单栏 -->
    <el-aside width="200px">
      <el-menu
        active-text-color="#ffd04b"
        background-color="#232323"
        text-color="#ffffff"
        unique-opened
        router
      >
        <!-- 个人基本健康情况 -->
        <el-sub-menu index="0">
          <template #title>
            <el-icon>
              <UserFilled />
            </el-icon>
            <span>个人基本情况</span>
          </template>

          <!-- 个人信息展示 -->
          <el-menu-item index="/health/show">
            <span>个人基本信息</span>
          </el-menu-item>

          <!-- 个人信息可视化 -->
          <el-menu-item index="/health/visualization">
            <span>个人信息可视化</span>
          </el-menu-item>

          <!-- 个人体检报告 -->
          <el-menu-item index="/health/report">
            <span>个人体检报告</span>
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

          <el-menu-item index="/nutrient/all">
            <span>所有营养素</span>
          </el-menu-item>
        </el-sub-menu>

        <!-- 运动板块 -->
        <el-sub-menu index="2">
          <template #title>
            <el-icon>
              <Flag />
            </el-icon>
            <span>运动板块</span>
          </template>

          <el-menu-item index="/exercise/records">
            <span>运动记录</span>
          </el-menu-item>

          <el-menu-item index="/exercise/sumTime">
            <span>运动统计</span>
          </el-menu-item>
        </el-sub-menu>

        <!-- 饮食日记板块 -->
        <el-sub-menu index="3">
          <template #title>
            <el-icon>
              <Notebook />
            </el-icon>
            <span>饮食运动日记</span>
          </template>
          
          <el-menu-item index="/diet/show">
            <span>饮食录入</span>
          </el-menu-item>

          <el-menu-item index="/diet/diary">
            <span>饮食运动日记</span>
          </el-menu-item>

        </el-sub-menu>

        <!-- 每日健康面板 -->
        <el-menu-item>
          <el-icon>
            <PieChart />
          </el-icon>
          <span>每日健康</span>
        </el-menu-item>

        <!-- 推荐食物 -->
        <el-menu-item>
          <el-icon>
            <Food />
          </el-icon>
          <span>营养餐推荐</span>
        </el-menu-item>

        <!-- 个人信息管理 -->
        <el-sub-menu index="6">
          <template #title>
            <el-icon>
              <UserFilled />
            </el-icon>
            <span>账号中心</span>
          </template>

          <el-menu-item index="/user/info">
            <el-icon>
              <User />
            </el-icon>
            <span>基本资料</span>
          </el-menu-item>

          <el-menu-item index="/user/avatar">
            <el-icon>
              <Crop />
            </el-icon>
            <span>更换头像</span>
          </el-menu-item>

          <el-menu-item index="/user/resetPassword">
            <el-icon>
              <EditPen />
            </el-icon>
            <span>重置密码</span>
          </el-menu-item>
        </el-sub-menu>

      </el-menu>
    </el-aside>

    <!-- 右侧主区域 -->
    <el-container>

      <!-- 头部区域 -->
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
            <el-dropdown-item command="info" :icon="User">基本资料</el-dropdown-item>
            <el-dropdown-item command="avatar" :icon="Crop">更换头像</el-dropdown-item>
            <el-dropdown-item command="resetPassword" :icon="EditPen">重置密码</el-dropdown-item>
            <el-dropdown-item command="logout" :icon="SwitchButton">退出登录</el-dropdown-item>
          </template>
        </el-dropdown>
      </el-header>

      <!-- 主体内容区域 -->
      <el-main>
        <router-view></router-view>
      </el-main>

      <!-- 底部区域 -->
      <el-footer class="el-footer">长安健康系统 ©2025 Created by XuChangan</el-footer>

    </el-container>

  </el-container>
</template>

<script setup>
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
  Food
} from '@element-plus/icons-vue'
import avatar from '@/assets/default.png'

import { useUserInfoStore } from '@/stores/userInfo.js';
const userInfoStore = useUserInfoStore();
import { useTokenStore } from '@/stores/token.js';
const tokenStore = useTokenStore();
import { useRouter } from 'vue-router';
const router = useRouter();
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
