import request from '@/utils/request'

// 查询基地管理教学实践列表
export function listJdgljxsj(query) {
  return request({
    url: '/jdgl/jdgljxsj/list',
    method: 'get',
    params: query
  })
}

// 查询基地管理教学实践详细
export function getJdgljxsj(id) {
  return request({
    url: '/jdgl/jdgljxsj/' + id,
    method: 'get'
  })
}

// 新增基地管理教学实践
export function addJdgljxsj(data) {
  return request({
    url: '/jdgl/jdgljxsj',
    method: 'post',
    data: data
  })
}

// 修改基地管理教学实践
export function updateJdgljxsj(data) {
  return request({
    url: '/jdgl/jdgljxsj',
    method: 'put',
    data: data
  })
}

// 删除基地管理教学实践
export function delJdgljxsj(id) {
  return request({
    url: '/jdgl/jdgljxsj/' + id,
    method: 'delete'
  })
}

// 导出基地管理教学实践
export function exportJdgljxsj(query) {
  return request({
    url: '/jdgl/jdgljxsj/export',
    method: 'get',
    params: query
  })
}