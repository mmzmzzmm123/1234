import request from '@/utils/request'

// 查询微信账号列表
export function listWxAccount(query) {
  return request({
    url: '/custom/wxAccount/list',
    method: 'get',
    params: query
  })
}

// 查询微信账号详细
export function getWxAccount(id) {
  return request({
    url: '/custom/wxAccount/' + id,
    method: 'get'
  })
}

// 新增微信账号
export function addWxAccount(data) {
  return request({
    url: '/custom/wxAccount',
    method: 'post',
    data: data
  })
}

// 修改微信账号
export function updateWxAccount(data) {
  return request({
    url: '/custom/wxAccount',
    method: 'put',
    data: data
  })
}

// 删除微信账号
export function delWxAccount(id) {
  return request({
    url: '/custom/wxAccount/' + id,
    method: 'delete'
  })
}

// 导出微信账号
export function exportWxAccount(query) {
  return request({
    url: '/custom/wxAccount/export',
    method: 'get',
    params: query
  })
}
