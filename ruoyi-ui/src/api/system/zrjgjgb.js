import request from '@/utils/request'

// 查询个体工商户信贷_主题库_准入监管结果列表
export function listZrjgjgb(query) {
  return request({
    url: '/system/zrjgjgb/list',
    method: 'get',
    params: query
  })
}

// 查询个体工商户信贷_主题库_准入监管结果详细
export function getZrjgjgb(tyshxydm) {
  return request({
    url: '/system/zrjgjgb/' + tyshxydm,
    method: 'get'
  })
}

// 新增个体工商户信贷_主题库_准入监管结果
export function addZrjgjgb(data) {
  return request({
    url: '/system/zrjgjgb',
    method: 'post',
    data: data
  })
}

// 修改个体工商户信贷_主题库_准入监管结果
export function updateZrjgjgb(data) {
  return request({
    url: '/system/zrjgjgb',
    method: 'put',
    data: data
  })
}

// 删除个体工商户信贷_主题库_准入监管结果
export function delZrjgjgb(tyshxydm) {
  return request({
    url: '/system/zrjgjgb/' + tyshxydm,
    method: 'delete'
  })
}

// 导出个体工商户信贷_主题库_准入监管结果
export function exportZrjgjgb(query) {
  return request({
    url: '/system/zrjgjgb/export',
    method: 'get',
    params: query
  })
}