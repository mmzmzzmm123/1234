import request from '@/utils/request'

// 查询圈子列表
export function listOrbit(query) {
  return request({
    url: '/system/orbit/list',
    method: 'get',
    params: query
  })
}

// 查询圈子详细
export function getOrbit(quanId) {
  return request({
    url: '/system/orbit/' + quanId,
    method: 'get'
  })
}

// 新增圈子
export function addOrbit(data) {
  return request({
    url: '/system/orbit',
    method: 'post',
    data: data
  })
}

// 修改圈子
export function updateOrbit(data) {
  return request({
    url: '/system/orbit',
    method: 'put',
    data: data
  })
}

// 删除圈子
export function delOrbit(quanId) {
  return request({
    url: '/system/orbit/' + quanId,
    method: 'delete'
  })
}

// 导出圈子
export function exportOrbit(query) {
  return request({
    url: '/system/orbit/export',
    method: 'get',
    params: query
  })
}