import request from '@/utils/request'

// 查询基地管理自我发展列表
export function listJdglzwfz(query) {
  return request({
    url: '/jdgl/jdglzwfz/list',
    method: 'get',
    params: query
  })
}

// 查询基地管理自我发展详细
export function getJdglzwfz(id) {
  return request({
    url: '/jdgl/jdglzwfz/' + id,
    method: 'get'
  })
}

// 新增基地管理自我发展
export function addJdglzwfz(data) {
  return request({
    url: '/jdgl/jdglzwfz',
    method: 'post',
    data: data
  })
}

// 修改基地管理自我发展
export function updateJdglzwfz(data) {
  return request({
    url: '/jdgl/jdglzwfz',
    method: 'put',
    data: data
  })
}

// 删除基地管理自我发展
export function delJdglzwfz(id) {
  return request({
    url: '/jdgl/jdglzwfz/' + id,
    method: 'delete'
  })
}

// 导出基地管理自我发展
export function exportJdglzwfz(query) {
  return request({
    url: '/jdgl/jdglzwfz/export',
    method: 'get',
    params: query
  })
}