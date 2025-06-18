
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

// 获取健康报告
const getHealthReportsService = (params)=>{
  let urlParams = new URLSearchParams()
  for (const key in params) {
    urlParams.append(key, params[key]);
  }
  console.log('getHealthReportsService', urlParams.toString())
  return request.post('/health/getReports', urlParams)
}

// 上传报告
const uploadHealthReportService = (reportData)=>{
  const params = new URLSearchParams()
  for (const key in reportData) {
    params.append(key, reportData[key]);
  }
  return request.post('/health/uploadReport', params)
}

export {
  getHealthInfoService,
  updateHealthInfoService,
  getAvgHealthInfoService,
  getHealthReportsService,
  uploadHealthReportService
}
