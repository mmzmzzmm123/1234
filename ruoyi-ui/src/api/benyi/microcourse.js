import request from '@/utils/request'

// 查询微型课程列表
export function listMicrocourse(query) {
  return request({
    url: '/benyi/microcourse/list',
    method: 'get',
    params: query
  })
}

// 查询流程下拉树结构
export function treeselect() {
  return request({
      url: '/benyi/microcourse/treeselect',
      method: 'get'
  })
}

// 查询微型课程详细
export function getMicrocourse(id) {
  return request({
    url: '/benyi/microcourse/' + id,
    method: 'get'
  })
}

// 新增微型课程
export function addMicrocourse(data) {
  return request({
    url: '/benyi/microcourse',
    method: 'post',
    data: data
  })
}

// 修改微型课程
export function updateMicrocourse(data) {
  return request({
    url: '/benyi/microcourse',
    method: 'put',
    data: data
  })
}

// 删除微型课程
export function delMicrocourse(id) {
  return request({
    url: '/benyi/microcourse/' + id,
    method: 'delete'
  })
}

// 导出微型课程
export function exportMicrocourse(query) {
  return request({
    url: '/system/microcourse/export',
    method: 'get',
    params: query
  })
}