import request from '@/utils/request'

// 查询用户等级记录列表
export function listUserLevel(query) {
  return request({
    url: '/user/userLevel/list',
    method: 'get',
    params: query
  })
}

// 查询用户等级记录详细
export function getUserLevel(id) {
  return request({
    url: '/user/userLevel/' + id,
    method: 'get'
  })
}

// 新增用户等级记录
export function addUserLevel(data) {
  return request({
    url: '/user/userLevel',
    method: 'post',
    data: data
  })
}

// 修改用户等级记录
export function updateUserLevel(data) {
  return request({
    url: '/user/userLevel',
    method: 'put',
    data: data
  })
}

// 删除用户等级记录
export function delUserLevel(id) {
  return request({
    url: '/user/userLevel/' + id,
    method: 'delete'
  })
}
