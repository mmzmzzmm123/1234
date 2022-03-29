import request from '@/utils/request'

// 查询支付相关列表
export function listWxPay(query) {
  return request({
    url: '/pay/wxPay/list',
    method: 'get',
    params: query
  })
}

// 查询支付相关详细
export function getWxPay(id) {
  return request({
    url: '/pay/wxPay/' + id,
    method: 'get'
  })
}

// 新增支付相关
export function addWxPay(data) {
  return request({
    url: '/pay/wxPay',
    method: 'post',
    data: data
  })
}

// 修改支付相关
export function updateWxPay(data) {
  return request({
    url: '/pay/wxPay',
    method: 'put',
    data: data
  })
}

// 删除支付相关
export function delWxPay(id) {
  return request({
    url: '/pay/wxPay/' + id,
    method: 'delete'
  })
}
