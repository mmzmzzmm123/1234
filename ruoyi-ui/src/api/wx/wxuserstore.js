import request from '@/utils/request'

// 查询微信用户余额列表
export function listWxuserstore(query) {
  return request({
    url: '/wx/wxuserstore/list',
    method: 'get',
    params: query
  })
}

// 查询微信用户余额详细
export function getWxuserstore(id) {
  return request({
    url: '/wx/wxuserstore/' + id,
    method: 'get'
  })
}

// 新增微信用户余额
export function addWxuserstore(data) {
  return request({
    url: '/wx/wxuserstore',
    method: 'post',
    data: data
  })
}

// 修改微信用户余额
export function updateWxuserstore(data) {
  return request({
    url: '/wx/wxuserstore',
    method: 'put',
    data: data
  })
}

// 删除微信用户余额
export function delWxuserstore(id) {
  return request({
    url: '/wx/wxuserstore/' + id,
    method: 'delete'
  })
}
