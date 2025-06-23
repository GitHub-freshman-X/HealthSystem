
import request from '@/utils/request.js'

// 获取所有营养素列表
const getAllNutrientsService = (params)=>{
  let urlParams = new URLSearchParams();
  for(const key in params) {
    urlParams.append(key, params[key]);
  }
  return request.post('/nutrient/list', urlParams)
}

// 添加
const addNutrientService = (nutrient)=>{
  return request.post('/nutrient/add', nutrient)
}

// 删除
const deleteNutrientService = (nutrientId)=>{
  return request.delete('/nutrient/delete', {
    params: { nutrientId }
  });
}

// 更新
const updateNutrientService = (nutrient)=>{
  return request.put('/nutrient/update', nutrient)
}

// 获取推荐值
const getSupplyRecommendService = (params)=>{
  let urlParams = new URLSearchParams();
  for(const key in params) {
    urlParams.append(key, params[key]);
  }
  return request.post('/nutrient/supplyRecommend', urlParams)
}

// 常见食物所含营养素
const getFoodNutrientService = (params)=>{
  let urlParams = new URLSearchParams();
  for(const key in params) {
    urlParams.append(key, params[key]);
  }
  return request.post('/nutrient/foodNutrient', urlParams)
}

// 包含指定营养素的食物
const getFoodByNutrientService = (params)=>{
  let urlParams = new URLSearchParams();
  for(const key in params) {
    urlParams.append(key, params[key]);
  }
  return request.post('/nutrient/foodByNutrient', urlParams)
}

// 根据功能查询
const getNutrientsByFunctionService = (params)=>{
  let urlParams = new URLSearchParams();
  for(const key in params) {
    urlParams.append(key, params[key]);
  }
  return request.post('/nutrient/nutrientsByFunction', urlParams)
}

export {
  getAllNutrientsService,
  addNutrientService,
  deleteNutrientService,
  updateNutrientService,
  getSupplyRecommendService,
  getFoodNutrientService,
  getFoodByNutrientService,
  getNutrientsByFunctionService
}
