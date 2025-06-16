
import request from '@/utils/request.js'

// 登录接口
const userLoginService = (loginData) =>{
  return request.post('/user/login', loginData)
}

// 注册接口
const userRegisterService = (registerData) =>{
  return request.post('/user/register', registerData)
}

export {
  userLoginService,
  userRegisterService
}
