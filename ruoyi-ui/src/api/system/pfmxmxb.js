import request from '@/utils/request'

// 查询个体工商户信贷_标准库_评分模型明细列表
export function listPfmxmxb(query) {
  return request({
    url: '/system/pfmxmxb/list',
    method: 'get',
    params: query
  })
}

// 查询个体工商户信贷_标准库_评分模型明细详细
export function getPfmxmxb(tyshxydm) {
  return request({
    url: '/system/pfmxmxb/' + tyshxydm,
    method: 'get'
  })
}

// 新增个体工商户信贷_标准库_评分模型明细
export function addPfmxmxb(data) {
  return request({
    url: '/system/pfmxmxb',
    method: 'post',
    data: data
  })
}

// 修改个体工商户信贷_标准库_评分模型明细
export function updatePfmxmxb(data) {
  return request({
    url: '/system/pfmxmxb',
    method: 'put',
    data: data
  })
}

// 删除个体工商户信贷_标准库_评分模型明细
export function delPfmxmxb(tyshxydm) {
  return request({
    url: '/system/pfmxmxb/' + tyshxydm,
    method: 'delete'
  })
}

// 导出个体工商户信贷_标准库_评分模型明细
export function exportPfmxmxb(query) {
  return request({
    url: '/system/pfmxmxb/export',
    method: 'get',
    params: query
  })
}