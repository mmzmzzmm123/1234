import request from '@/utils/request'

// 查询订单明细列表
export function listOrderDetails(query) {
  return request({
    url: '/order/orderDetails/list',
    method: 'get',
    params: query
  })
}

// 查询订单明细详细
export function getOrderDetails(id) {
  return request({
    url: '/order/orderDetails/' + id,
    method: 'get'
  })
}

// 新增订单明细
export function addOrderDetails(data) {
  return request({
    url: '/order/orderDetails',
    method: 'post',
    data: data
  })
}

// 修改订单明细
export function updateOrderDetails(data) {
  return request({
    url: '/order/orderDetails',
    method: 'put',
    data: data
  })
}

// 删除订单明细
export function delOrderDetails(id) {
  return request({
    url: '/order/orderDetails/' + id,
    method: 'delete'
  })
}
