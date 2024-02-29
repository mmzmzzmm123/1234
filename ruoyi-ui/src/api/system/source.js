import request from '@/utils/request'

// 查询车源管理列表
export function listSource(query) {
  return request({
    url: '/system/source/list',
    method: 'get',
    params: query
  })
}

// 查询车源管理详细
export function getSource(id) {
  return request({
    url: '/system/source/' + id,
    method: 'get'
  })
}

// 新增车源管理
export function addSource(data) {
  return request({
    url: '/system/source',
    method: 'post',
    data: data
  })
}

// 修改车源管理
export function updateSource(data) {
  return request({
    url: '/system/source',
    method: 'put',
    data: data
  })
}

// 删除车源管理
export function delSource(id) {
  return request({
    url: '/system/source/' + id,
    method: 'delete'
  })
}
