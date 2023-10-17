import request from '@/utils/request'

// 查询微信公众号用户列表
export function listUserOfficialAccount(query) {
  return request({
    url: '/user/userOfficialAccount/list',
    method: 'get',
    params: query
  })
}

// 查询微信公众号用户详细
export function getUserOfficialAccount(id) {
  return request({
    url: '/user/userOfficialAccount/' + id,
    method: 'get'
  })
}

// 拉取微信公众号用户列表
export function pullNewUserList(){
  return request({
    url: '/user/userOfficialAccount/pullNewUserList/',
    method: 'get'
  })
}

// 批量更新新用户基本信息
export function updateNewUserInfo(){
  return request({
    url: '/user/userOfficialAccount/updateNewUserInfo/',
    method: 'get'
  })
}



// 新增微信公众号用户
export function addUserOfficialAccount(data) {
  return request({
    url: '/user/userOfficialAccount',
    method: 'post',
    data: data
  })
}

// 修改微信公众号用户
export function updateUserOfficialAccount(data) {
  return request({
    url: '/user/userOfficialAccount',
    method: 'put',
    data: data
  })
}

// 删除微信公众号用户
export function delUserOfficialAccount(id) {
  return request({
    url: '/user/userOfficialAccount/' + id,
    method: 'delete'
  })
}
