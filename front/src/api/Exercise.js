import request from '@/utils/request.js'

// 获取所有运动记录
const getAllExerciseRecordsService = (params)=>{
  const urlParams = new URLSearchParams();
  for(let key in params){
    urlParams.append(key, params[key]);
  }
  return request.post('/exercise/allRecords', urlParams)
}

// 上传运动记录
const uploadRecordService = (record)=>{
  return request.post('/exercise/upload', record)
}

export {
  getAllExerciseRecordsService,
  uploadRecordService
}
