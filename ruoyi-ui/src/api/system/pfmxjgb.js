import request from '@/utils/request'

// 查询个体工商户信贷_主题库_评分模型结果列表
export function listPfmxjgb(query) {
  return request({
    url: '/system/pfmxjgb/list',
    method: 'get',
    params: query
  })
}

// 查询个体工商户信贷_主题库_评分模型结果详细
export function getPfmxjgb(tyshxydm) {
  return request({
    url: '/system/pfmxjgb/' + tyshxydm,
    method: 'get'
  })
}

// 新增个体工商户信贷_主题库_评分模型结果
export function addPfmxjgb(data) {
  return request({
    url: '/system/pfmxjgb',
    method: 'post',
    data: data
  })
}

// 修改个体工商户信贷_主题库_评分模型结果
export function updatePfmxjgb(data) {
  return request({
    url: '/system/pfmxjgb',
    method: 'put',
    data: data
  })
}

// 删除个体工商户信贷_主题库_评分模型结果
export function delPfmxjgb(tyshxydm) {
  return request({
    url: '/system/pfmxjgb/' + tyshxydm,
    method: 'delete'
  })
}

// 导出个体工商户信贷_主题库_评分模型结果
export function exportPfmxjgb(query) {
  return request({
    url: '/system/pfmxjgb/export',
    method: 'get',
    params: query
  })
}