
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

// 上传头像
const uploadAvatarService = (avatarFile)=>{
  const params = new URLSearchParams()
  params.append('avatarUrl', avatarFile);
  return request.patch('/user/uploadAvatar', params)
}

// 修改密码
const updatePasswordService = (oldPassword, newPassword)=>{
  const params = new URLSearchParams()
  params.append('oldPassword', oldPassword);
  params.append('newPassword', newPassword);
  return request.patch('/user/updatePassword', params)
}

// 查家庭成员
const getFamilyMembersService = ()=>{
  return request.get('/user/familyMembers')
}

// 登录家庭成员给账号
const switchLoginService = (memberUserId)=>{
  const params = new URLSearchParams()
  params.append('memberUserId', memberUserId);
  console.log('switchLoginService', params.toString())
  return request.post('/user/switchLogin', params)
}

// 注册家庭成员
const registerFamilyMember = (registerData)=>{
  const params = new URLSearchParams();
  params.append('username', registerData.value.username);
  params.append('password', registerData.value.password);
  params.append('memberUserRole', registerData.value.memberUserRole);
  return request.post('/user/registerFamilyMember', params);
}

export {
  userLoginService,
  userRegisterService,
  getUserInfoService,
  updateUserInfoService,
  uploadAvatarService,
  updatePasswordService,
  getFamilyMembersService,
  switchLoginService,
  registerFamilyMember
}
