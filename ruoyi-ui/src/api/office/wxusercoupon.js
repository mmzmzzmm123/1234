import request from '@/utils/request'

// 查询用户优惠券列表
export function listWxusercoupon(query) {
  return request({
    url: '/office/wxusercoupon/list',
    method: 'get',
    params: query
  })
}

// 查询用户优惠券详细
export function getWxusercoupon(id) {
  return request({
    url: '/office/wxusercoupon/' + id,
    method: 'get'
  })
}

// 新增用户优惠券
export function addWxusercoupon(data) {
  return request({
    url: '/office/wxusercoupon',
    method: 'post',
    data: data
  })
}

// 修改用户优惠券
export function updateWxusercoupon(data) {
  return request({
    url: '/office/wxusercoupon',
    method: 'put',
    data: data
  })
}

// 删除用户优惠券
export function delWxusercoupon(id) {
  return request({
    url: '/office/wxusercoupon/' + id,
    method: 'delete'
  })
}
