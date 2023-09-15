import request from '@/utils/request'

// 查询支付单退款列表
export function listPaymentRefund(query) {
  return request({
    url: '/payment/paymentRefund/list',
    method: 'get',
    params: query
  })
}

// 查询支付单退款详细
export function getPaymentRefund(id) {
  return request({
    url: '/payment/paymentRefund/' + id,
    method: 'get'
  })
}

// 新增支付单退款
export function addPaymentRefund(data) {
  return request({
    url: '/payment/paymentRefund',
    method: 'post',
    data: data
  })
}

// 修改支付单退款
export function updatePaymentRefund(data) {
  return request({
    url: '/payment/paymentRefund',
    method: 'put',
    data: data
  })
}

// 删除支付单退款
export function delPaymentRefund(id) {
  return request({
    url: '/payment/paymentRefund/' + id,
    method: 'delete'
  })
}
