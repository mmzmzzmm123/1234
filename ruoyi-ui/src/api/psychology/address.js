import request from '@/utils/request'

// 查询咨询师地址列表
export function listAddress(query) {
  return request({
    url: '/finance/address/list',
    method: 'get',
    params: query
  })
}

// 查询咨询师地址详细
export function getAddress(addressId) {
  return request({
    url: '/finance/address/' + addressId,
    method: 'get'
  })
}

// 新增咨询师地址
export function addAddress(data) {
  return request({
    url: '/finance/address',
    method: 'post',
    data: data
  })
}

// 修改咨询师地址
export function updateAddress(data) {
  return request({
    url: '/finance/address',
    method: 'put',
    data: data
  })
}

// 删除咨询师地址
export function delAddress(addressId) {
  return request({
    url: '/finance/address/' + addressId,
    method: 'delete'
  })
}
