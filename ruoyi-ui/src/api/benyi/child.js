import request from '@/utils/request'

// 查询幼儿信息列表
export function listChild(query) {
  return request({
    url: '/benyi/child/list',
    method: 'get',
    params: query
  })
}

// 查询幼儿信息列表
export function listByCheck(query) {
  return request({
    url: '/benyi/child/listByCheck',
    method: 'get',
    params: query
  })
}

// 查询幼儿信息详细
export function getChild(id) {
  return request({
    url: '/benyi/child/' + id,
    method: 'get'
  })
}

// 新增幼儿信息
export function addChild(data) {
  return request({
    url: '/benyi/child',
    method: 'post',
    data: data
  })
}

// 修改幼儿信息
export function updateChild(data) {
  return request({
    url: '/benyi/child',
    method: 'put',
    data: data
  })
}

// 修改幼儿信息
export function updateChild_tb(data,id) {
  return request({
    url: '/benyi/child/'+id,
    method: 'put',
    data: data
  })
}

// 删除幼儿信息
export function delChild(id) {
  return request({
    url: '/benyi/child/' + id,
    method: 'delete'
  })
}

// 导出幼儿信息
export function exportChild(query) {
  return request({
    url: '/benyi/child/export',
    method: 'get',
    params: query
  })
}