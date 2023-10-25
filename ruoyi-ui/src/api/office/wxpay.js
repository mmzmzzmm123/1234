import request from '@/utils/request'

// 查询微信支付配置列表
export function listWxpay(query) {
  return request({
    url: '/office/wxpay/list',
    method: 'get',
    params: query
  })
}

// 查询微信支付配置详细
export function getWxpay(id) {
  return request({
    url: '/office/wxpay/' + id,
    method: 'get'
  })
}

// 新增微信支付配置
export function addWxpay(data) {
  return request({
    url: '/office/wxpay',
    method: 'post',
    data: data
  })
}

// 修改微信支付配置
export function updateWxpay(data) {
  return request({
    url: '/office/wxpay',
    method: 'put',
    data: data
  })
}

// 删除微信支付配置
export function delWxpay(id) {
  return request({
    url: '/office/wxpay/' + id,
    method: 'delete'
  })
}
