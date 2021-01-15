import request from '@/utils/request'

// 查询调研指导列表
export function listJyydyzd(query) {
  return request({
    url: '/jyykhgl/jyydyzd/list',
    method: 'get',
    params: query
  })
}

// 查询调研指导详细
export function getJyydyzd(id) {
  return request({
    url: '/jyykhgl/jyydyzd/' + id,
    method: 'get'
  })
}

// 新增调研指导
export function addJyydyzd(data) {
  return request({
    url: '/jyykhgl/jyydyzd',
    method: 'post',
    data: data
  })
}

// 修改调研指导
export function updateJyydyzd(data) {
  return request({
    url: '/jyykhgl/jyydyzd',
    method: 'put',
    data: data
  })
}

// 删除调研指导
export function delJyydyzd(id) {
  return request({
    url: '/jyykhgl/jyydyzd/' + id,
    method: 'delete'
  })
}

// 导出调研指导
export function exportJyydyzd(query) {
  return request({
    url: '/jyykhgl/jyydyzd/export',
    method: 'get',
    params: query
  })
}