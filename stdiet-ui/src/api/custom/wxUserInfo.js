import request from '@/utils/request'

// 查询微信用户列表
export function listWxUserInfo(query) {
  return request({
    url: '/custom/wxUserInfo/list',
    method: 'get',
    params: query
  })
}

// 查询微信用户详细
export function getWxUserInfo(openid) {
  return request({
    url: '/custom/wxUserInfo/' + openid,
    method: 'get'
  })
}

// 新增微信用户
export function addWxUserInfo(data) {
  return request({
    url: '/custom/wxUserInfo',
    method: 'post',
    data: data
  })
}

// 修改微信用户
export function updateWxUserInfo(data) {
  return request({
    url: '/custom/wxUserInfo',
    method: 'put',
    data: data
  })
}

// 删除微信用户
export function delWxUserInfo(openid) {
  return request({
    url: '/custom/wxUserInfo/' + openid,
    method: 'delete'
  })
}

// 导出微信用户
export function exportWxUserInfo(query) {
  return request({
    url: '/custom/wxUserInfo/export',
    method: 'get',
    params: query
  })
}