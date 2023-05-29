import request from '@/utils/request'

// 查询微信用户信息列表
export function listWxuser(query) {
  return request({
    url: '/office/wxuser/list',
    method: 'get',
    params: query
  })
}

// 查询微信用户信息详细
export function getWxuser(id) {
  return request({
    url: '/office/wxuser/' + id,
    method: 'get'
  })
}

// 新增微信用户信息
export function addWxuser(data) {
  return request({
    url: '/office/wxuser',
    method: 'post',
    data: data
  })
}

// 修改微信用户信息
export function updateWxuser(data) {
  return request({
    url: '/office/wxuser',
    method: 'put',
    data: data
  })
}

// 删除微信用户信息
export function delWxuser(id) {
  return request({
    url: '/office/wxuser/' + id,
    method: 'delete'
  })
}
