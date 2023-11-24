import request from '@/utils/request'

// 查询咨询师入驻申请列表
export function getItemList(query) {
  return request({
    url: '/psychology/bill/getItemList',
    method: 'get',
    params: query
  })
}

// 查询咨询师入驻申请列表
export function getItemListForDetail(query) {
  return request({
    url: '/psychology/bill/getItemListForDetail',
    method: 'get',
    params: query
  })
}

// 查询咨询师入驻申请列表
export function listBill(query) {
  return request({
    url: '/psychology/bill/list',
    method: 'get',
    params: query
  })
}

// 查询咨询师入驻申请详细
export function getBill(id) {
  return request({
    url: '/psychology/bill/' + id,
    method: 'get'
  })
}

// 修改咨询师入驻申请
export function updateBill(data) {
  return request({
    url: '/psychology/bill',
    method: 'put',
    data: data
  })
}

// 新增咨询服务配置
export function createUser(id) {
  return request({
    url: '/psychology/bill/createUser/' + id,
    method: 'post',
  })
}

// 删除咨询师入驻申请
export function delBill(id) {
  return request({
    url: '/psychology/bill/' + id,
    method: 'delete'
  })
}
