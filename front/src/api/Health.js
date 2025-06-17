
import request from '@/utils/request.js'

// 获取当前用户的健康数据
const getHealthInfoService = ()=>{
  return request.get('/health/info')
}

// 更新当前用户的健康数据
const updateHealthInfoService = (healthInfo)=>{
  return request.put('/health/update', healthInfo)
}

export {
  getHealthInfoService,
  updateHealthInfoService
}
