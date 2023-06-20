import request from '@/utils/request'

// 查询课程订单列表
export function listOrder(query) {
  return request({
    url: '/gauge/order/query',
    method: 'post',
    data: query
  })
}

// 查询课程订单详细
export function getOrder(id) {
  return request({
    url: '/gauge/order/' + id,
    method: 'get'
  })
}

// 新增课程订单
export function addOrder(data) {
  return request({
    url: '/gauge/order',
    method: 'post',
    data: data
  })
}

// 修改课程订单
export function updateOrder(data) {
  return request({
    url: '/gauge/order',
    method: 'put',
    data: data
  })
}

// 删除课程订单
export function delOrder(id) {
  return request({
    url: '/gauge/order/' + id,
    method: 'delete'
  })
}
