import request from '@/utils/request'

// 查询全球国家管理列表
export function listCountries(query) {
  return request({
    url: '/system/countries/list',
    method: 'get',
    params: query
  })
}

// 查询全球国家管理详细
export function getCountries(id) {
  return request({
    url: '/system/countries/' + id,
    method: 'get'
  })
}

// 新增全球国家管理
export function addCountries(data) {
  return request({
    url: '/system/countries',
    method: 'post',
    data: data
  })
}

// 修改全球国家管理
export function updateCountries(data) {
  return request({
    url: '/system/countries',
    method: 'put',
    data: data
  })
}

// 删除全球国家管理
export function delCountries(id) {
  return request({
    url: '/system/countries/' + id,
    method: 'delete'
  })
}
