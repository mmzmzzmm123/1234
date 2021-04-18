import request from '@/utils/request'

// 登录方法
export function login(username, password, code, uuid) {
  const data = {
    username,
    password,
    code,
    uuid
  }
  return request({
    url: '/login',
    method: 'post',
    data: data
  })
}

// 获取用户详细信息
export function getInfo() {
  return request({
    url: '/getInfo',
    method: 'get'
  })
}

// 退出方法
export function logout() {
  return request({
    url: '/logout',
    method: 'post'
  })
}

// 获取验证码
export function getCodeImg() {
  return request({
    url: '/captchaImage',
    method: 'get'
  })
}


// 注册账号方法
export function registerUser(username, password, phone, phoneCode) {
  const data = {
    username,
    password,
    phone,
    phoneCode
  }
  return request({
    url: '/registerUser',
    method: 'post',
    data: data
  })
}


// 获取手机号验证码注册
export function getregisterUser(phone) {
  var query={
    phone:phone
  }
  return request({
    url: '/registerPhoneCode',
    method: 'get',
    params:query
  })
}
