import { defineStore } from 'pinia';
import { ref } from 'vue';

const useHealthInfoStore = defineStore('useHealthInfo',()=>{
  // 1. 响应式变量
  const healthInfo = ref({});

  // 2. 获取健康信息
  const getHealthInfo = ()=>{
    return healthInfo.value;
  }

  // 3. 设置健康信息
  const setHealthInfo = (newHealthInfo)=>{
    healthInfo.value = newHealthInfo;
  }

  // 4. 清除健康信息
  const clearHealthInfo = ()=>{
    healthInfo.value = {};
  }

  // 5. 返回
  return {
    healthInfo,
    getHealthInfo,
    setHealthInfo,
    clearHealthInfo
  }
},{
  persist: true
})

export { useHealthInfoStore };
