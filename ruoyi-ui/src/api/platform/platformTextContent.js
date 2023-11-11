import request from '@/utils/request'

// 查询文本内容管理列表
export function listPlatformTextContent(query) {
  return request({
    url: '/platform/platformTextContent/list',
    method: 'get',
    params: query
  })
}

// 查询文本内容管理详细
export function getPlatformTextContent(id) {
  return request({
    url: '/platform/platformTextContent/' + id,
    method: 'get'
  })
}

// 新增文本内容管理
export function addPlatformTextContent(data) {
  return request({
    url: '/platform/platformTextContent',
    method: 'post',
    data: data
  })
}

// 修改文本内容管理
export function updatePlatformTextContent(data) {
  return request({
    url: '/platform/platformTextContent',
    method: 'put',
    data: data
  })
}

// 删除文本内容管理
export function delPlatformTextContent(id) {
  return request({
    url: '/platform/platformTextContent/' + id,
    method: 'delete'
  })
}
