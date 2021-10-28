import request from '@/utils/request'

// 查询图片管理。管理上传的图片列表
export function listVisualImage(query) {
  return request({
    url: '/system/visualImage/list',
    method: 'get',
    params: query
  })
}

// 查询图片管理。管理上传的图片详细
export function getVisualImage(id) {
  return request({
    url: '/system/visualImage/' + id,
    method: 'get'
  })
}

// 新增图片管理。管理上传的图片
export function addVisualImage(data) {
  return request({
    url: '/system/visualImage',
    method: 'post',
    data: data
  })
}

// 修改图片管理。管理上传的图片
export function updateVisualImage(data) {
  return request({
    url: '/system/visualImage',
    method: 'put',
    data: data
  })
}

// 删除图片管理。管理上传的图片
export function delVisualImage(id) {
  return request({
    url: '/system/visualImage/' + id,
    method: 'delete'
  })
}

// 导出图片管理。管理上传的图片
export function exportVisualImage(query) {
  return request({
    url: '/system/visualImage/export',
    method: 'get',
    params: query
  })
}

// 导出图片管理。管理上传的图片
export function uploadImage(query) {
  return request({
    url: '/system/visualImage/upload',
    method: 'post',
    params: query
  })
}
