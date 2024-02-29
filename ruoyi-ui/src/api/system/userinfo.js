import request from '@/utils/request'

// 查询用户展示信息列表
export function listUserinfo(query) {
  return request({
    url: '/system/userinfo/list',
    method: 'get',
    params: query
  })
}

// 查询用户展示信息详细
export function getUserinfo(id) {
  return request({
    url: '/system/userinfo/' + id,
    method: 'get'
  })
}

// 新增用户展示信息
export function addUserinfo(data) {
  return request({
    url: '/system/userinfo',
    method: 'post',
    data: data
  })
}

// 修改用户展示信息
export function updateUserinfo(data) {
  return request({
    url: '/system/userinfo',
    method: 'put',
    data: data
  })
}

// 删除用户展示信息
export function delUserinfo(id) {
  return request({
    url: '/system/userinfo/' + id,
    method: 'delete'
  })
}
