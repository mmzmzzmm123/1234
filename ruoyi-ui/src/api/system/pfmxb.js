import request from '@/utils/request'

// 查询福建省泉州市个体工商户信贷_主题库_评分模型列表
export function listPfmxb(query) {
  return request({
    url: '/system/pfmxb/list',
    method: 'get',
    params: query
  })
}

// 查询福建省泉州市个体工商户信贷_主题库_评分模型详细
export function getPfmxb(zjlid) {
  return request({
    url: '/system/pfmxb/' + zjlid,
    method: 'get'
  })
}

// 新增福建省泉州市个体工商户信贷_主题库_评分模型
export function addPfmxb(data) {
  return request({
    url: '/system/pfmxb',
    method: 'post',
    data: data
  })
}

// 修改福建省泉州市个体工商户信贷_主题库_评分模型
export function updatePfmxb(data) {
  return request({
    url: '/system/pfmxb',
    method: 'put',
    data: data
  })
}

// 删除福建省泉州市个体工商户信贷_主题库_评分模型
export function delPfmxb(zjlid) {
  return request({
    url: '/system/pfmxb/' + zjlid,
    method: 'delete'
  })
}

// 导出福建省泉州市个体工商户信贷_主题库_评分模型
export function exportPfmxb(query) {
  return request({
    url: '/system/pfmxb/export',
    method: 'get',
    params: query
  })
}