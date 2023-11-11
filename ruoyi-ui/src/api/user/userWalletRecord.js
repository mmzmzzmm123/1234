import request from '@/utils/request'

// 查询用户钱包记录列表
export function listUserWalletRecord(query) {
  return request({
    url: '/user/userWalletRecord/list',
    method: 'get',
    params: query
  })
}

// 查询用户钱包记录详细
export function getUserWalletRecord(id) {
  return request({
    url: '/user/userWalletRecord/' + id,
    method: 'get'
  })
}

// 新增用户钱包记录
export function addUserWalletRecord(data) {
  return request({
    url: '/user/userWalletRecord',
    method: 'post',
    data: data
  })
}

// 修改用户钱包记录
export function updateUserWalletRecord(data) {
  return request({
    url: '/user/userWalletRecord',
    method: 'put',
    data: data
  })
}

// 删除用户钱包记录
export function delUserWalletRecord(id) {
  return request({
    url: '/user/userWalletRecord/' + id,
    method: 'delete'
  })
}
