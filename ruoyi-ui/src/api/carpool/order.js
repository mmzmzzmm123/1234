import request from '@/utils/request'

// 查询订单信息列表
export function listOrder(query) {
  return request({
    url: '/carpool/order/list',
    method: 'get',
    params: query
  })
}

// 查询订单信息详细
export function getOrder(id) {
  return request({
    url: '/carpool/order/' + id,
    method: 'get'
  })
}

// 新增订单信息
export function addOrder(data) {
  return request({
    url: '/carpool/order',
    method: 'post',
    data: data
  })
}

// 修改订单信息
export function updateOrder(data) {
  return request({
    url: '/carpool/order',
    method: 'put',
    data: data
  })
}

// 删除订单信息
export function delOrder(id) {
  return request({
    url: '/carpool/order/' + id,
    method: 'delete'
  })
}

// 导出订单信息
export function exportOrder(query) {
  return request({
    url: '/carpool/order/export',
    method: 'get',
    params: query
  })
}