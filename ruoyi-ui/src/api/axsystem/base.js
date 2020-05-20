import request from '@/utils/request'

// 查询用户基础信息列表
export function listBase(query) {
  return request({
    url: '/axsystem/base/list',
    method: 'get',
    params: query
  })
}

// 查询用户基础信息详细
export function getBase(uid) {
  return request({
    url: '/axsystem/base/' + uid,
    method: 'get'
  })
}

// 新增用户基础信息
export function addBase(data) {
  return request({
    url: '/axsystem/base',
    method: 'post',
    data: data
  })
}

// 修改用户基础信息
export function updateBase(data) {
  return request({
    url: '/axsystem/base',
    method: 'put',
    data: data
  })
}

// 删除用户基础信息
export function delBase(uid) {
  return request({
    url: '/axsystem/base/' + uid,
    method: 'delete'
  })
}

// 导出用户基础信息
export function exportBase(query) {
  return request({
    url: '/axsystem/base/export',
    method: 'get',
    params: query
  })
}