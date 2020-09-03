import request from '@/utils/request'

// 查询见习之星名单列表
export function listJxzxKhjd(query) {
  return request({
    url: '/jxzxkhgl/statistics/list',
    method: 'get',
    params: query
  })
}

// 查询见习之星名单详细
export function getJxzxKhjd(id) {
  return request({
    url: '/jxzxkhgl/statistics/' + id,
    method: 'get'
  })
}