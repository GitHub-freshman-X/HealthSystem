
import request from '@/utils/request.js'

// 登录接口
const userLoginService = (loginData) =>{
  return request.post('/user/login', loginData)
}

// 注册接口
const userRegisterService = (registerData) =>{
  return request.post('/user/register', registerData)
}

// 获取用户基本信息
const getUserInfoService = ()=>{
  return request.get('/user/info')
}

// 更新用户信息
const updateUserInfoService = (userInfo)=>{
  return request.put('/user/update', userInfo)
}

export {
  userLoginService,
  userRegisterService,
  getUserInfoService,
  updateUserInfoService
}
