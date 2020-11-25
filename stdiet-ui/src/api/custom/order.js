import request from '@/utils/request'

// 查询销售订单列表
export function listOrder(params) {
  return request({
    url: '/custom/order/list',
    method: 'get',
    params: params
  })
}

// 查询销售订单详细
export function getOrder(orderId) {
  return request({
    url: '/custom/order/' + orderId,
    method: 'get'
  })
}

// 新增销售订单
export function addOrder(data) {
  return request({
    url: '/custom/order',
    method: 'post',
    data: data
  })
}

// 修改销售订单
export function updateOrder(data) {
  return request({
    url: '/custom/order',
    method: 'put',
    data: data
  })
}

// 删除销售订单
export function delOrder(orderId) {
  return request({
    url: '/custom/order/' + orderId,
    method: 'delete'
  })
}

// 导出销售订单
export function exportOrder(query) {
  return request({
    url: '/custom/order/export',
    method: 'get',
    params: query
  })
}

export function getOptions() {
  return request({
    url: '/custom/post/options',
    method: 'get'
  })
}
