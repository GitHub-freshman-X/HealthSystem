
import request from '@/utils/request.js'

// 获取当前用户的健康数据
const getHealthInfoService = ()=>{
  return request.get('/health/info')
}

// 更新当前用户的健康数据
const updateHealthInfoService = (healthInfo)=>{
  return request.put('/health/update', healthInfo)
}

// 获取平均水平
const getAvgHealthInfoService = (gender, age)=>{
  const params = new URLSearchParams()
  params.append('gender', gender);
  params.append('age', age);
  return request.post('/health/avg', params)
}

export {
  getHealthInfoService,
  updateHealthInfoService,
  getAvgHealthInfoService
}
