import request from '@/utils/request'

// 查询全景管理列表
export function listPanorama(query) {
  return request({
    url: '/system/panorama/list',
    method: 'get',
    params: query
  })
}

// 查询全景管理详细
export function getPanorama(id) {
  return request({
    url: '/system/panorama/' + id,
    method: 'get'
  })
}

// 新增全景管理
export function addPanorama(data) {
  return request({
    url: '/system/panorama',
    method: 'post',
    data: data
  })
}

// 修改全景管理
export function updatePanorama(data) {
  return request({
    url: '/system/panorama',
    method: 'put',
    data: data
  })
}

// 删除全景管理
export function delPanorama(id) {
  return request({
    url: '/system/panorama/' + id,
    method: 'delete'
  })
}

// 导出全景管理
export function exportPanorama(query) {
  return request({
    url: '/system/panorama/export',
    method: 'get',
    params: query
  })
}