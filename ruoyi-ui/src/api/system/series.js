import request from '@/utils/request'

// 查询车系管理列表
export function listSeries(query) {
  return request({
    url: '/system/series/list',
    method: 'get',
    params: query
  })
}

// 查询车系管理详细
export function getSeries(id) {
  return request({
    url: '/system/series/' + id,
    method: 'get'
  })
}

// 新增车系管理
export function addSeries(data) {
  return request({
    url: '/system/series',
    method: 'post',
    data: data
  })
}

// 修改车系管理
export function updateSeries(data) {
  return request({
    url: '/system/series',
    method: 'put',
    data: data
  })
}

// 删除车系管理
export function delSeries(id) {
  return request({
    url: '/system/series/' + id,
    method: 'delete'
  })
}
