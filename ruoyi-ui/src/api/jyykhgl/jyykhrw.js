import request from '@/utils/request'

// 查询教研员考核任务列表
export function listJyykhrw(query) {
  return request({
    url: '/jyykhgl/jyykhrw/list',
    method: 'get',
    params: query
  })
}

// 查询教研员考核任务列表
export function listJyykhrwstatistics(query) {
  return request({
    url: '/jyykhgl/jyykhrw/statistics',
    method: 'get',
    params: query
  })
}

// 查询教研员考核任务详细
export function getJyykhrw(id) {
  return request({
    url: '/jyykhgl/jyykhrw/' + id,
    method: 'get'
  })
}

// 新增教研员考核任务
export function addJyykhrw(data) {
  return request({
    url: '/jyykhgl/jyykhrw',
    method: 'post',
    data: data
  })
}

// 修改教研员考核任务
export function updateJyykhrw(data) {
  return request({
    url: '/jyykhgl/jyykhrw',
    method: 'put',
    data: data
  })
}

// 删除教研员考核任务
export function delJyykhrw(id) {
  return request({
    url: '/jyykhgl/jyykhrw/' + id,
    method: 'delete'
  })
}

// 导出教研员考核任务
export function exportJyykhrw(query) {
  return request({
    url: '/jyykhgl/jyykhrw/export',
    method: 'get',
    params: query
  })
}
