import request from '@/utils/request'

// 查询航船状态列表
export function listStatus(query) {
  return request({
    url: '/usv/status/list',
    method: 'get',
    params: query
  })
}

// 查询航船状态详细
export function getStatus(id) {
  return request({
    url: '/usv/status/' + id,
    method: 'get'
  })
}

// 新增航船状态
export function addStatus(data) {
  return request({
    url: '/usv/status',
    method: 'post',
    data: data
  })
}

// 修改航船状态
export function updateStatus(data) {
  return request({
    url: '/usv/status',
    method: 'put',
    data: data
  })
}

// 删除航船状态
export function delStatus(id) {
  return request({
    url: '/usv/status/' + id,
    method: 'delete'
  })
}

// 导出航船状态
export function exportStatus(query) {
  return request({
    url: '/usv/status/export',
    method: 'get',
    params: query
  })
}