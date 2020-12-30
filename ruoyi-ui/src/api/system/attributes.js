import request from '@/utils/request'

// 查询电子文件信息列表
export function listAttributes(query) {
  return request({
    url: '/system/attributes/list',
    method: 'get',
    params: query
  })
}

// 查询电子文件信息详细
export function getAttributes(id) {
  return request({
    url: '/system/attributes/' + id,
    method: 'get'
  })
}
// 获取电子文件信息base64
export function getAttributesBase64(id) {
  return request({
    url: '/system/attributes/base64/' + id,
    method: 'get'
  })
}

// 新增电子文件信息
export function addAttributes(data) {
  return request({
    url: '/system/attributes',
    method: 'post',
    data: data
  })
}

// 修改电子文件信息
export function updateAttributes(data) {
  return request({
    url: '/system/attributes',
    method: 'put',
    data: data
  })
}

// 删除电子文件信息
export function delAttributes(id) {
  return request({
    url: '/system/attributes/' + id,
    method: 'delete'
  })
}

// 导出电子文件信息
export function exportAttributes(query) {
  return request({
    url: '/system/attributes/export',
    method: 'get',
    params: query
  })
}
