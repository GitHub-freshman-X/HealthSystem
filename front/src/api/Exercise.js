import request from '@/utils/request.js'

// 获取所有运动记录
const getAllExerciseRecordsService = (params)=>{
  const urlParams = new URLSearchParams();
  for(let key in params){
    urlParams.append(key, params[key]);
  }
  // console.log('getAllExerciseRecordsService: ', urlParams.toString())
  return request.post('/exercise/allRecords', urlParams)
}

// 上传运动记录
const uploadRecordService = (record)=>{
  return request.post('/exercise/upload', record)
}

const getSumTimeService = (params)=>{
  const urlParams = new URLSearchParams()
  for (const key in params) {
    urlParams.append(key, params[key]);
  }
  return request.post('/exercise/getSumTime', urlParams)
}

export {
  getAllExerciseRecordsService,
  uploadRecordService,
  getSumTimeService
}
