import request from '@/utils/request'

// 查询见习之星名单列表
export function listJxzxmd(query) {
  return request({
    url: '/jxjs/jxzxmd/list',
    method: 'get',
    params: query
  })
}

// 查询见习之星名单详细
export function getJxzxmd(id) {
  return request({
    url: '/jxjs/jxzxmd/' + id,
    method: 'get'
  })
}

// 查询见习之星名单详细
export function getJxzxmdInfo(id) {
  return request({
    url: '/jxjs/jxzxmd/zm/' + id,
    method: 'get'
  })
}

// 导出见习之星名单
export function exportJxzxmd(query) {
  return request({
    url: '/jxjs/jxzxmd/export',
    method: 'get',
    params: query
  })
}
