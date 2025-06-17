
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

export {
  getAllNutrientsService,
  addNutrientService,
  deleteNutrientService,
  updateNutrientService
}
