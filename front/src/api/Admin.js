import request from '@/utils/request.js'

const getAllUsersService = (params)=>{
  const urlParams = new URLSearchParams()
  for (const key in params) {
    urlParams.append(key, params[key])
  }
  // console.log('urlParams', urlParams.toString())
  return request.post('/admin/allUsers', urlParams)
}

const updateUserService = (user)=>{
  return request.post('/admin/updateUser', user)
}

const deleteUserService = (userId)=>{
  const urlParams = new URLSearchParams()
  urlParams.append('userId', userId)
  return request.delete('/admin/deleteUser', urlParams)
}

const getAllFoodsService = (params)=>{
  const urlParams = new URLSearchParams()
  for(const key in params){
    urlParams.append(key, params[key])
  }
  return request.post('/admin/allFoods', urlParams)
}

const updateFoodService = (params)=>{
  return request.post('/admin/updateFood', params)
}

const deleteFoodService = (foodId)=>{
  const urlParams = new URLSearchParams()
  urlParams.append('foodId', foodId)
  return request.post('/admin/deleteFood', urlParams)
}

const uploadFoodService = (params)=>{
  return request.post('/admin/uploadFood', params)
}

export {
  getAllUsersService,
  updateUserService,
  deleteUserService,
  getAllFoodsService,
  updateFoodService,
  deleteFoodService,
  uploadFoodService,
}
