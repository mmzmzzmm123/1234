import request from '@/utils/request'

// 查询微信用户余额列表
export function listWxuseramount(query) {
  return request({
    url: '/office/wxuseramount/list',
    method: 'get',
    params: query
  })
}

// 查询微信用户余额详细
export function getWxuseramount(id) {
  return request({
    url: '/office/wxuseramount/' + id,
    method: 'get'
  })
}

// 新增微信用户余额
export function addWxuseramount(data) {
  return request({
    url: '/office/wxuseramount',
    method: 'post',
    data: data
  })
}

// 修改微信用户余额
export function updateWxuseramount(data) {
  return request({
    url: '/office/wxuseramount',
    method: 'put',
    data: data
  })
}

// 删除微信用户余额
export function delWxuseramount(id) {
  return request({
    url: '/office/wxuseramount/' + id,
    method: 'delete'
  })
}
