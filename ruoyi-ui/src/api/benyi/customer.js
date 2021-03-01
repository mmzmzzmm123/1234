import request from '@/utils/request'

// 查询本一-客户关系管理列表
export function listCustomer(query) {
  return request({
    url: '/benyi/customer/list',
    method: 'get',
    params: query
  })
}

// 查询本一-客户关系管理详细
export function getCustomer(id) {
  return request({
    url: '/benyi/customer/' + id,
    method: 'get'
  })
}

// 新增本一-客户关系管理
export function addCustomer(data) {
  return request({
    url: '/benyi/customer',
    method: 'post',
    data: data
  })
}

// 修改本一-客户关系管理
export function updateCustomer(data) {
  return request({
    url: '/benyi/customer',
    method: 'put',
    data: data
  })
}

// 删除本一-客户关系管理
export function delCustomer(id) {
  return request({
    url: '/benyi/customer/' + id,
    method: 'delete'
  })
}

// 导出本一-客户关系管理
export function exportCustomer(query) {
  return request({
    url: '/benyi/customer/export',
    method: 'get',
    params: query
  })
}