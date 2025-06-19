import request from '@/utils/request.js'

// 获取所有饮食记录
const getDietFoodsService = (params)=>{
  const urlParams = new URLSearchParams();
  for(let key in params){
    urlParams.append(key, params[key]);
  }
  console.log('getDietFoodsService: ', urlParams.toString())
  return request.post('/diet/getDietFoods', urlParams)
}

// 上传饮食记录
const uploadDietFoodsService = (record)=>{
  console.log('uploadDietFoodsService: ', record)
  return request.post('/diet/uploadDietFoods', record)
}

export {
  getDietFoodsService,
  uploadDietFoodsService
}
