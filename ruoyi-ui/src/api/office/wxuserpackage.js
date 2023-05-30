import request from '@/utils/request'

// 查询用户套餐购买记录列表
export function listWxuserpackage(query) {
  return request({
    url: '/office/wxuserpackage/list',
    method: 'get',
    params: query
  })
}

// 查询用户套餐购买记录详细
export function getWxuserpackage(id) {
  return request({
    url: '/office/wxuserpackage/' + id,
    method: 'get'
  })
}

// 新增用户套餐购买记录
export function addWxuserpackage(data) {
  return request({
    url: '/office/wxuserpackage',
    method: 'post',
    data: data
  })
}

// 修改用户套餐购买记录
export function updateWxuserpackage(data) {
  return request({
    url: '/office/wxuserpackage',
    method: 'put',
    data: data
  })
}

// 删除用户套餐购买记录
export function delWxuserpackage(id) {
  return request({
    url: '/office/wxuserpackage/' + id,
    method: 'delete'
  })
}
