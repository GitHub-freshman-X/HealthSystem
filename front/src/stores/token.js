
import { defineStore } from 'pinia';
import { ref } from 'vue';

const useTokenStore = defineStore('token', ()=>{
  // 1. 响应式变量
  const token = ref('')
  
  // 2. 定义函数，修改token
  const setToken = (newToken) => {
    token.value = newToken
  }

  // 3. 定义函数，获取token
  const getToken = () => {
    return token.value
  }

  // 4. 定义函数，清除token
  const clearToken = () => {
    token.value = ''
  }

  return {
    token,
    setToken,
    getToken,
    clearToken
  }
}, {
  persist: true
})

export { useTokenStore };
