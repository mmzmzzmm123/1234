import request from '@/utils/request'

// 查询微信用户信息列表
export function listWechatUser(query) {
  return request({
    url: '/system/wechatUser/list',
    method: 'get',
    params: query
  })
}

// 查询微信用户信息详细
export function getWechatUser(userId) {
  return request({
    url: '/system/wechatUser/' + userId,
    method: 'get'
  })
}

// 新增微信用户信息
export function addWechatUser(data) {
  return request({
    url: '/system/wechatUser',
    method: 'post',
    data: data
  })
}

// 修改微信用户信息
export function updateWechatUser(data) {
  return request({
    url: '/system/wechatUser',
    method: 'put',
    data: data
  })
}

// 删除微信用户信息
export function delWechatUser(userId) {
  return request({
    url: '/system/wechatUser/' + userId,
    method: 'delete'
  })
}

// 导出微信用户信息
export function exportWechatUser(query) {
  return request({
    url: '/system/wechatUser/export',
    method: 'get',
    params: query
  })
}