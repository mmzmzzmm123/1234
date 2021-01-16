import request from '@/utils/request'

// 查询其他考核（教研员）列表
export function listJyyqtkh(query) {
  return request({
    url: '/jyykhgl/jyyqtkh/list',
    method: 'get',
    params: query
  })
}

// 查询其他考核（教研员）详细
export function getJyyqtkh(id) {
  return request({
    url: '/jyykhgl/jyyqtkh/' + id,
    method: 'get'
  })
}

// 新增其他考核（教研员）
export function addJyyqtkh(data) {
  return request({
    url: '/jyykhgl/jyyqtkh',
    method: 'post',
    data: data
  })
}

// 修改其他考核（教研员）
export function updateJyyqtkh(data) {
  return request({
    url: '/jyykhgl/jyyqtkh',
    method: 'put',
    data: data
  })
}

// 删除其他考核（教研员）
export function delJyyqtkh(id) {
  return request({
    url: '/jyykhgl/jyyqtkh/' + id,
    method: 'delete'
  })
}

// 导出其他考核（教研员）
export function exportJyyqtkh(query) {
  return request({
    url: '/jyykhgl/jyyqtkh/export',
    method: 'get',
    params: query
  })
}