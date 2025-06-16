//定制请求的实例

//导入axios  npm install axios
import axios from 'axios';
import { ElMessage } from 'element-plus'
//定义一个变量,记录公共的前缀  ,  baseURL
const baseURL = '/api';
const instance = axios.create({baseURL})
import { useTokenStore } from '@/stores/token';
import router from '@/router/index.js';

//添加响应拦截器
instance.interceptors.response.use(
  result=>{
    if(result.data.code == 0){
        return result.data;
    }else {
    //   alert(result.data.message || '服务异常');
      ElMessage.error(result.data.message || '服务异常');
      return Promise.reject(result.data);//异步的状态转化成失败的状态
    }
  },
  err=>{
    // 判断响应状态码，特别处理401
    if(err.response && err.response.status==401){
      ElMessage.error('登录已失效，请重新登录');
      router.push('/login')
    }else {
      ElMessage.error('服务异常');
    }
    return Promise.reject(err);//异步的状态转化成失败的状态
  }
)

// 添加请求拦截器
instance.interceptors.request.use(
  (config) => {
    // 请求前的回调
    const tokenStore = useTokenStore();
    if(tokenStore.getToken()){
      // 如果有token,就设置请求头
      config.headers['Authorization'] = tokenStore.getToken();
    }
    return config;
  },
  (err) => {
    Promise.reject(err);
  }
)

export default instance;
