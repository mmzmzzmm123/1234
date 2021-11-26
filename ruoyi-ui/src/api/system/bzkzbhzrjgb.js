import request from '@/utils/request'

// 查询个体工商户信贷_标准库_准入监管列表
export function listBzkzbhzrjgb(query) {
  return request({
    url: '/system/bzkzbhzrjgb/list',
    method: 'get',
    params: query
  })
}

// 查询个体工商户信贷_标准库_准入监管详细
export function getBzkzbhzrjgb(zjlid) {
  return request({
    url: '/system/bzkzbhzrjgb/' + zjlid,
    method: 'get'
  })
}

// 新增个体工商户信贷_标准库_准入监管
export function addBzkzbhzrjgb(data) {
  return request({
    url: '/system/bzkzbhzrjgb',
    method: 'post',
    data: data
  })
}

// 修改个体工商户信贷_标准库_准入监管
export function updateBzkzbhzrjgb(data) {
  return request({
    url: '/system/bzkzbhzrjgb',
    method: 'put',
    data: data
  })
}

// 删除个体工商户信贷_标准库_准入监管
export function delBzkzbhzrjgb(zjlid) {
  return request({
    url: '/system/bzkzbhzrjgb/' + zjlid,
    method: 'delete'
  })
}

// 导出个体工商户信贷_标准库_准入监管
export function exportBzkzbhzrjgb(query) {
  return request({
    url: '/system/bzkzbhzrjgb/export',
    method: 'get',
    params: query
  })
}