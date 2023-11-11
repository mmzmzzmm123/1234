import request from '@/utils/request'

// 查询支付订单列表
export function listPaymentOrder(query) {
  return request({
    url: '/payment/paymentOrder/list',
    method: 'get',
    params: query
  })
}

// 查询支付订单详细
export function getPaymentOrder(id) {
  return request({
    url: '/payment/paymentOrder/' + id,
    method: 'get'
  })
}

// 新增支付订单
export function addPaymentOrder(data) {
  return request({
    url: '/payment/paymentOrder',
    method: 'post',
    data: data
  })
}

// 修改支付订单
export function updatePaymentOrder(data) {
  return request({
    url: '/payment/paymentOrder',
    method: 'put',
    data: data
  })
}

// 删除支付订单
export function delPaymentOrder(id) {
  return request({
    url: '/payment/paymentOrder/' + id,
    method: 'delete'
  })
}
