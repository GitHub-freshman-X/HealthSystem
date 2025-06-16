import { defineStore } from 'pinia';
import { ref } from 'vue';

const useUserInfoStore = defineStore('userInfo', ()=>{
  // 1. 响应式变量
  const userInfo = ref({})

  // 2. 获取用户基本信息
  const getUserInfo = ()=>{
    return userInfo.value;
  }
  
  // 3. 设置用户基本信息
  const setUserInfo = (newUserInfo)=>{
    userInfo.value = newUserInfo;
  }

  // 4. 清除用户基本信息
  const clearUserInfo = ()=>{
    userInfo.value = {};
  }

  // 5. 返回
  return {
    userInfo,
    getUserInfo,
    setUserInfo,
    clearUserInfo
  }

},{
  persist: true
})

export { useUserInfoStore };
