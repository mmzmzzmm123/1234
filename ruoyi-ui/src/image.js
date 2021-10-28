import request from '@/utils/request'

// 查询图片管理。管理上传的图片列表
export function listImage(query) {
  return request({
    url: '/system/image/list',
    method: 'get',
    params: query
  })
}

// 查询图片管理。管理上传的图片详细
export function getImage(id) {
  return request({
    url: '/system/image/' + id,
    method: 'get'
  })
}

// 新增图片管理。管理上传的图片
export function addImage(data) {
  return request({
    url: '/system/image',
    method: 'post',
    data: data
  })
}

// 修改图片管理。管理上传的图片
export function updateImage(data) {
  return request({
    url: '/system/image',
    method: 'put',
    data: data
  })
}

// 删除图片管理。管理上传的图片
export function delImage(id) {
  return request({
    url: '/system/image/' + id,
    method: 'delete'
  })
}

// 导出图片管理。管理上传的图片
export function exportImage(query) {
  return request({
    url: '/system/image/export',
    method: 'get',
    params: query
  })
}