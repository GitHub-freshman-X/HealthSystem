
import request from '@/utils/request.js'

// 获取所有营养素列表
const getAllNutrientsService = ()=>{
  return request.get('/nutrient/list')
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

export {
  getAllNutrientsService,
  addNutrientService,
  deleteNutrientService,
  updateNutrientService,
  getSupplyRecommendService
}
