<!-- 大致分成左边菜单栏，右边展示内容 -->
<template>
  <el-container class="layout-container">
    <!-- 左侧菜单栏 -->
    <el-aside width="200px">
      <el-menu active-text-color="#ffd04b" background-color="#232323" text-color="#ffffff" router>
        <!-- 暂时不知道还有啥 -->
        <el-menu-item>
          <span>1</span>
        </el-menu-item>

        <el-menu-item>
          <span>2</span>
        </el-menu-item>

        <el-menu-item>
          <span>3</span>
        </el-menu-item>

        <!-- 个人信息管理 -->
        <el-sub-menu>
          <template #title>
            <el-icon>
              <UserFilled />
            </el-icon>
            <span>个人中心</span>
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
      <el-header></el-header>

      <!-- 主体内容区域 -->
      <el-main>
        <router-view></router-view>
      </el-main>

      <!-- 底部区域 -->
      <el-footer class="el-footer">健康系统 ©2025 Created by XuChangan</el-footer>

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
  CaretBottom
} from '@element-plus/icons-vue'

import { getUserInfoService } from '@/api/User.js'
import { useUserInfoStore } from '@/stores/userInfo'
const userInfoStore = useUserInfoStore()
// 获取用户信息
const getUserInfo = async()=>{
  let result = await getUserInfoService()
  // 存储到pinia中
  userInfoStore.setUserInfo(result.data)
}
getUserInfo()

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
