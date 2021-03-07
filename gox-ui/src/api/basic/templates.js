import request from '@/utils/request'

// 查询模板维护列表
export function listTemplates(query) {
  return request({
    url: '/basic/templates/list',
    method: 'get',
    params: query
  })
}

// 查询模板维护详细
export function getTemplates(id) {
  return request({
    url: '/basic/templates/' + id,
    method: 'get'
  })
}

// 新增模板维护
export function addTemplates(data) {
  return request({
    url: '/basic/templates',
    method: 'post',
    data: data
  })
}

// 修改模板维护
export function updateTemplates(data) {
  return request({
    url: '/basic/templates',
    method: 'put',
    data: data
  })
}
export function updateTemplatesBatch(data) {
  return request({
    url: '/basic/templates/batch',
    method: 'put',
    data: data
  })
}

// 删除模板维护
export function delTemplates(id) {
  return request({
    url: '/basic/templates/' + id,
    method: 'delete'
  })
}

// 导出模板维护
export function exportTemplates(query) {
  return request({
    url: '/basic/templates/export',
    method: 'get',
    params: query
  })
}
