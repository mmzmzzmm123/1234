import request from '@/utils/request'

// 查询地址详情列表
export function listAddress(query) {
  return request({
    url: '/carpool/address/list',
    method: 'get',
    params: query
  })
}

// 查询地址详情详细
export function getAddress(id) {
  return request({
    url: '/carpool/address/' + id,
    method: 'get'
  })
}

// 新增地址详情
export function addAddress(data) {
  return request({
    url: '/carpool/address',
    method: 'post',
    data: data
  })
}

// 修改地址详情
export function updateAddress(data) {
  return request({
    url: '/carpool/address',
    method: 'put',
    data: data
  })
}

// 删除地址详情
export function delAddress(id) {
  return request({
    url: '/carpool/address/' + id,
    method: 'delete'
  })
}

// 导出地址详情
export function exportAddress(query) {
  return request({
    url: '/carpool/address/export',
    method: 'get',
    params: query
  })
}
