import request from '@/utils/request'

// 查询元数据定义规则列表
export function listMetadataRule(query) {
  return request({
    url: '/system/metadataRule/list',
    method: 'get',
    params: query
  })
}
// 查询元数据定义规则列表
export function listDefinition(nameCn) {
  return request({
    url: '/system/metadataRule/definition/'+nameCn,
    method: 'get',
  })
}

// 查询元数据定义规则详细
export function getMetadataRule(id) {
  return request({
    url: '/system/metadataRule/' + id,
    method: 'get'
  })
}

// 新增元数据定义规则
export function addMetadataRule(data) {
  return request({
    url: '/system/metadataRule',
    method: 'post',
    data: data
  })
}

// 修改元数据定义规则
export function updateMetadataRule(data) {
  return request({
    url: '/system/metadataRule',
    method: 'put',
    data: data
  })
}

// 删除元数据定义规则
export function delMetadataRule(id) {
  return request({
    url: '/system/metadataRule/' + id,
    method: 'delete'
  })
}

// 导出元数据定义规则
export function exportMetadataRule(query) {
  return request({
    url: '/system/metadataRule/export',
    method: 'get',
    params: query
  })
}
