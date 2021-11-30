import request from '@/utils/request'

// 查询个体工商户信贷_标准库_准入监管明细列表
export function listZrjgmxb(query) {
  return request({
    url: '/system/zrjgmxb/list',
    method: 'get',
    params: query
  })
}

// 查询个体工商户信贷_标准库_准入监管明细详细
export function getZrjgmxb(tyshxydm) {
  return request({
    url: '/system/zrjgmxb/' + tyshxydm,
    method: 'get'
  })
}

// 新增个体工商户信贷_标准库_准入监管明细
export function addZrjgmxb(data) {
  return request({
    url: '/system/zrjgmxb',
    method: 'post',
    data: data
  })
}

// 修改个体工商户信贷_标准库_准入监管明细
export function updateZrjgmxb(data) {
  return request({
    url: '/system/zrjgmxb',
    method: 'put',
    data: data
  })
}

// 删除个体工商户信贷_标准库_准入监管明细
export function delZrjgmxb(tyshxydm) {
  return request({
    url: '/system/zrjgmxb/' + tyshxydm,
    method: 'delete'
  })
}

// 导出个体工商户信贷_标准库_准入监管明细
export function exportZrjgmxb(query) {
  return request({
    url: '/system/zrjgmxb/export',
    method: 'get',
    params: query
  })
}