import request from '@/utils/request'

// 查询用户信息管理列表
export function listUserInfo(query) {
  return request({
    url: '/user/userInfo/list',
    method: 'get',
    params: query
  })
}

// 查询用户信息管理详细
export function getUserInfo(id) {
  return request({
    url: '/user/userInfo/' + id,
    method: 'get'
  })
}

// 新增用户信息管理
export function addUserInfo(data) {
  return request({
    url: '/user/userInfo',
    method: 'post',
    data: data
  })
}

// 修改用户信息管理
export function updateUserInfo(data) {
  return request({
    url: '/user/userInfo',
    method: 'put',
    data: data
  })
}

// 删除用户信息管理
export function delUserInfo(id) {
  return request({
    url: '/user/userInfo/' + id,
    method: 'delete'
  })
}
