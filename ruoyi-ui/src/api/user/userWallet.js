import request from '@/utils/request'

// 查询用户钱包管理列表
export function listUserWallet(query) {
  return request({
    url: '/user/userWallet/list',
    method: 'get',
    params: query
  })
}

// 查询用户钱包管理详细
export function getUserWallet(id) {
  return request({
    url: '/user/userWallet/' + id,
    method: 'get'
  })
}

// 新增用户钱包管理
export function addUserWallet(data) {
  return request({
    url: '/user/userWallet',
    method: 'post',
    data: data
  })
}

// 修改用户钱包管理
export function updateUserWallet(data) {
  return request({
    url: '/user/userWallet',
    method: 'put',
    data: data
  })
}

// 删除用户钱包管理
export function delUserWallet(id) {
  return request({
    url: '/user/userWallet/' + id,
    method: 'delete'
  })
}
