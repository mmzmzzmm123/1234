import request from '@/utils/request'

// 预登陆，获取RSA密钥
export function preLogin() {
  return new Promise(resolve => {
    request({
      url: '/preLogin',
      headers: {
        isToken: false
      },
      method: 'get'
    }).then(res =>{
      resolve(res.data)
    })
  })
}

// 登录方法
export function login(username, password, code, uuid, publicKey) {
  const data = {
    username,
    password,
    code,
    uuid,
    publicKey
  }
  return request({
    url: '/login',
    headers: {
      isToken: false
    },
    method: 'post',
    data: data
  })
}

// 注册方法
export function register(data) {
  return request({
    url: '/register',
    headers: {
      isToken: false
    },
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
    headers: {
      isToken: false
    },
    method: 'get',
    timeout: 20000
  })
}
