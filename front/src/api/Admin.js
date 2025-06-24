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
  return request.post('/admin/deleteUser', urlParams)
}

export {
  getAllUsersService,
  updateUserService,
  deleteUserService
}
