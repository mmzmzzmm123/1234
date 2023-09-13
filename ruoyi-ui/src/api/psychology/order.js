import request from '@/utils/request'

// 查询咨询订单列表
export function listOrder(query) {
  return request({
    url: '/psychology/order/list',
    method: 'get',
    params: query
  })
}

// 查询咨询订单详细
export function getOrder(id) {
  return request({
    url: '/psychology/order/getDetail/' + id,
    method: 'get'
  })
}

export function getInfo(id) {
  return request({
    url: '/psychology/order/' + id,
    method: 'get'
  })
}

// 核销
export function hx(data) {
  return request({
    url: '/psychology/order/hx',
    method: 'post',
    data: data
  })
}
// 订单转介
export function modifyRef(data) {
  return request({
    url: '/psychology/order/modifyRef',
    method: 'post',
    data: data
  })
}

// 改价
export function modifyPrice(data) {
  return request({
    url: '/psychology/order/modifyPrice',
    method: 'post',
    data: data
  })
}

// 新增咨询订单
export function addOrder(data) {
  return request({
    url: '/psychology/order',
    method: 'post',
    data: data
  })
}

// 修改咨询订单
export function finish(id) {
  return request({
    url: '/psychology/order/finish/' + id,
    method: 'get'
  })
}

// 备注咨询订单
export function doRemark(data) {
  return request({
    url: '/psychology/order/remark',
    method: 'post',
    data: data
  })
}

// 修改咨询订单
export function updateOrder(data) {
  return request({
    url: '/psychology/order',
    method: 'put',
    data: data
  })
}

// 删除咨询订单
export function delOrder(id) {
  return request({
    url: '/psychology/order/' + id,
    method: 'delete'
  })
}
