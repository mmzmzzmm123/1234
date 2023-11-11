import request from '@/utils/request'

// 查询用户充值记录列表
export function listUserRechargeRecord(query) {
  return request({
    url: '/user/userRechargeRecord/list',
    method: 'get',
    params: query
  })
}

// 查询用户充值记录详细
export function getUserRechargeRecord(id) {
  return request({
    url: '/user/userRechargeRecord/' + id,
    method: 'get'
  })
}

// 新增用户充值记录
export function addUserRechargeRecord(data) {
  return request({
    url: '/user/userRechargeRecord',
    method: 'post',
    data: data
  })
}

// 修改用户充值记录
export function updateUserRechargeRecord(data) {
  return request({
    url: '/user/userRechargeRecord',
    method: 'put',
    data: data
  })
}

// 删除用户充值记录
export function delUserRechargeRecord(id) {
  return request({
    url: '/user/userRechargeRecord/' + id,
    method: 'delete'
  })
}
