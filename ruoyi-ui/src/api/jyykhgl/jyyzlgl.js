import request from '@/utils/request'

// 查询质量管理（教研员）列表
export function listJyyzlgl(query) {
  return request({
    url: '/jyykhgl/jyyzlgl/list',
    method: 'get',
    params: query
  })
}

// 查询质量管理（教研员）列表
export function listJyyzlglstatistics(query) {
  return request({
    url: '/jyykhgl/jyyzlgl/liststatistics',
    method: 'get',
    params: query
  })
}

// 查询质量管理（教研员）详细
export function getJyyzlgl(id) {
  return request({
    url: '/jyykhgl/jyyzlgl/' + id,
    method: 'get'
  })
}

// 新增质量管理（教研员）
export function addJyyzlgl(data) {
  return request({
    url: '/jyykhgl/jyyzlgl',
    method: 'post',
    data: data
  })
}

// 修改质量管理（教研员）
export function updateJyyzlgl(data) {
  return request({
    url: '/jyykhgl/jyyzlgl',
    method: 'put',
    data: data
  })
}

// 删除质量管理（教研员）
export function delJyyzlgl(id) {
  return request({
    url: '/jyykhgl/jyyzlgl/' + id,
    method: 'delete'
  })
}

// 导出质量管理（教研员）
export function exportJyyzlgl(query) {
  return request({
    url: '/jyykhgl/jyyzlgl/export',
    method: 'get',
    params: query
  })
}
