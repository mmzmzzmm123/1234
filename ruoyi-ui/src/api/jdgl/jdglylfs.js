import request from '@/utils/request'

// 查询基地管理引领辐射列表
export function listJdglylfs(query) {
  return request({
    url: '/jdgl/jdglylfs/list',
    method: 'get',
    params: query
  })
}

// 查询基地管理引领辐射详细
export function getJdglylfs(id) {
  return request({
    url: '/jdgl/jdglylfs/' + id,
    method: 'get'
  })
}

// 新增基地管理引领辐射
export function addJdglylfs(data) {
  return request({
    url: '/jdgl/jdglylfs',
    method: 'post',
    data: data
  })
}

// 修改基地管理引领辐射
export function updateJdglylfs(data) {
  return request({
    url: '/jdgl/jdglylfs',
    method: 'put',
    data: data
  })
}

// 删除基地管理引领辐射
export function delJdglylfs(id) {
  return request({
    url: '/jdgl/jdglylfs/' + id,
    method: 'delete'
  })
}

// 导出基地管理引领辐射
export function exportJdglylfs(query) {
  return request({
    url: '/jdgl/jdglylfs/export',
    method: 'get',
    params: query
  })
}