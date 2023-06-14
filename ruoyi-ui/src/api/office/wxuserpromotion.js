import request from '@/utils/request'

// 查询用户优惠券 t_wx_user_promotion列表
export function listWxuserpromotion(query) {
  return request({
    url: '/office/wxuserpromotion/list',
    method: 'get',
    params: query
  })
}

// 查询用户优惠券 t_wx_user_promotion详细
export function getWxuserpromotion(id) {
  return request({
    url: '/office/wxuserpromotion/' + id,
    method: 'get'
  })
}

// 新增用户优惠券 t_wx_user_promotion
export function addWxuserpromotion(data) {
  return request({
    url: '/office/wxuserpromotion',
    method: 'post',
    data: data
  })
}

// 修改用户优惠券 t_wx_user_promotion
export function updateWxuserpromotion(data) {
  return request({
    url: '/office/wxuserpromotion',
    method: 'put',
    data: data
  })
}

// 删除用户优惠券 t_wx_user_promotion
export function delWxuserpromotion(id) {
  return request({
    url: '/office/wxuserpromotion/' + id,
    method: 'delete'
  })
}
