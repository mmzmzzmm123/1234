import request from '@/utils/request'

// 查询密码策略列表
export function listPassword(query) {
  return request({
    url: '/system/password/list',
    method: 'get',
    params: query
  })
}

// 查询密码策略详细
export function getPassword(id) {
  return request({
    url: '/system/password/' + id,
    method: 'get'
  })
}

// 新增密码策略
export function addPassword(data) {
  return request({
    url: '/system/password',
    method: 'post',
    data: data
  })
}

// 修改密码策略
export function updatePassword(data) {
  return request({
    url: '/system/password',
    method: 'put',
    data: data
  })
}

// 删除密码策略
export function delPassword(id) {
  return request({
    url: '/system/password/' + id,
    method: 'delete'
  })
}
