import request from '@/utils/request'

// 查询个体工商户信贷_主题库_准入监管列表
export function listZtkzbhzrjgb(query) {
  return request({
    url: '/system/ztkzbhzrjgb/list',
    method: 'get',
    params: query
  })
}

// 查询个体工商户信贷_主题库_准入监管详细
export function getZtkzbhzrjgb(zjlid) {
  return request({
    url: '/system/ztkzbhzrjgb/' + zjlid,
    method: 'get'
  })
}

// 新增个体工商户信贷_主题库_准入监管
export function addZtkzbhzrjgb(data) {
  return request({
    url: '/system/ztkzbhzrjgb',
    method: 'post',
    data: data
  })
}

// 修改个体工商户信贷_主题库_准入监管
export function updateZtkzbhzrjgb(data) {
  return request({
    url: '/system/ztkzbhzrjgb',
    method: 'put',
    data: data
  })
}

// 删除个体工商户信贷_主题库_准入监管
export function delZtkzbhzrjgb(zjlid) {
  return request({
    url: '/system/ztkzbhzrjgb/' + zjlid,
    method: 'delete'
  })
}

// 导出个体工商户信贷_主题库_准入监管
export function exportZtkzbhzrjgb(query) {
  return request({
    url: '/system/ztkzbhzrjgb/export',
    method: 'get',
    params: query
  })
}