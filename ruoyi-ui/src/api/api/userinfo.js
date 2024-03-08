import request from '@/utils/request'

// 查询小程序用户信息管理列表
export function listUserinfo(query) {
  return request({
    url: '/api/userinfo/list',
    method: 'get',
    params: query
  })
}

// 查询小程序用户信息管理详细
export function getUserinfo(id) {
  return request({
    url: '/api/userinfo/' + id,
    method: 'get'
  })
}

// 新增小程序用户信息管理
export function addUserinfo(data) {
  return request({
    url: '/api/userinfo',
    method: 'post',
    data: data
  })
}

// 修改小程序用户信息管理
export function updateUserinfo(data) {
  return request({
    url: '/api/userinfo',
    method: 'put',
    data: data
  })
}

// 删除小程序用户信息管理
export function delUserinfo(id) {
  return request({
    url: '/api/userinfo/' + id,
    method: 'delete'
  })
}
