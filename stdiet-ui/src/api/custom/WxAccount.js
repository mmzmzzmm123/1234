import request from '@/utils/request'

// 查询微信销售账号列表
export function listWxAccount(query) {
  return request({
    url: '/custom/WxAccount/list',
    method: 'get',
    params: query
  })
}

// 查询微信销售账号详细
export function getWxAccount(id) {
  return request({
    url: '/custom/WxAccount/' + id,
    method: 'get'
  })
}

// 新增微信销售账号
export function addWxAccount(data) {
  return request({
    url: '/custom/WxAccount',
    method: 'post',
    data: data
  })
}

// 修改微信销售账号
export function updateWxAccount(data) {
  return request({
    url: '/custom/WxAccount',
    method: 'put',
    data: data
  })
}

// 删除微信销售账号
export function delWxAccount(id) {
  return request({
    url: '/custom/WxAccount/' + id,
    method: 'delete'
  })
}

// 导出微信销售账号
export function exportWxAccount(query) {
  return request({
    url: '/custom/WxAccount/export',
    method: 'get',
    params: query
  })
}


