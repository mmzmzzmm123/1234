import request from '@/utils/request'

// 查询微信用户记录列表
export function listWxUserLog(query) {
  return request({
    url: '/custom/wxUserLog/list',
    method: 'get',
    params: query
  })
}

// 查询微信用户记录详细
export function getWxUserLog(openid) {
  return request({
    url: '/custom/wxUserLog/' + openid,
    method: 'get'
  })
}

// 新增微信用户记录
export function addWxUserLog(data) {
  return request({
    url: '/custom/wxUserLog',
    method: 'post',
    data: data
  })
}

// 修改微信用户记录
export function updateWxUserLog(data) {
  return request({
    url: '/custom/wxUserLog',
    method: 'put',
    data: data
  })
}

// 删除微信用户记录
export function delWxUserLog(openid) {
  return request({
    url: '/custom/wxUserLog/' + openid,
    method: 'delete'
  })
}

// 导出微信用户记录
export function exportWxUserLog(query) {
  return request({
    url: '/custom/wxUserLog/export',
    method: 'get',
    params: query
  })
}
