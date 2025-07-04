import request from '@/utils/request.js'

// 获取所有饮食记录
const getDietFoodsService = (params)=>{
  const urlParams = new URLSearchParams();
  for(let key in params){
    urlParams.append(key, params[key]);
  }
  // console.log('getDietFoodsService: ', urlParams.toString())
  return request.post('/diet/getDietFoods', urlParams)
}

// 获取每顿饭摄入的热量
const getDietCaloriesService = (params)=>{
  const urlParams = new URLSearchParams();
  for(let key in params){
    urlParams.append(key, params[key]);
  }
  console.log('getDietCaloriesService: ', urlParams.toString())
  return request.post('/diet/getDietCalories', urlParams)
}

// 上传饮食记录
const uploadDietFoodsService = (record)=>{
  // console.log('uploadDietFoodsService: ', record)
  return request.post('/diet/uploadDietFoods', record)
}

// 查日记
const getDietFoodsDiaryService = (params)=>{
  const urlParams = new URLSearchParams();
  for(let key in params){
    urlParams.append(key, params[key]);
  }
  // console.log('getDietFoodsDiaryService: ', urlParams.toString())
  return request.post('/diet/diary', urlParams)
}

// 上传日记
const uploadDietExerciseDiaryService = (diary)=>{
  return request.post('/diet/uploadDiary', diary)
}

// 获取营养素摄入含量
const getDietNutrientIntakeService = (params)=>{
  const urlParams = new URLSearchParams();
  for(let key in params){
    urlParams.append(key, params[key]);
  }
  // console.log('getDietNutrientIntakeService: ', urlParams.toString())
  return request.post('/diet/nutrientIntake', urlParams)
}

// 获取各个营养素标准值
const getNutrientSufficientService = (params)=>{
  const urlParams = new URLSearchParams();
  for(let key in params){
    urlParams.append(key, params[key]);
  }
  // console.log('getNutrientSufficientService: ', urlParams.toString())
  return request.post('/diet/nutrientSufficient', urlParams)
}

// 获取营养餐推荐
const getNutritiousMealService = (params)=>{
  const urlParams = new URLSearchParams();
  for(let key in params){
    urlParams.append(key, params[key]);
  }
  // console.log('getNutritiousMealService: ', urlParams.toString())
  return request.post('/diet/nutritiousMeal', urlParams)
}

export {
  getDietFoodsService,
  getDietCaloriesService,
  uploadDietFoodsService,
  getDietFoodsDiaryService,
  uploadDietExerciseDiaryService,
  getDietNutrientIntakeService,
  getNutrientSufficientService,
  getNutritiousMealService
}
