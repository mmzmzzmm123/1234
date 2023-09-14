import request from '@/utils/request'

// 查询用户社交账号管理列表
export function listUserSocialAccounts(query) {
  return request({
    url: '/user/userSocialAccounts/list',
    method: 'get',
    params: query
  })
}

// 查询用户社交账号管理详细
export function getUserSocialAccounts(id) {
  return request({
    url: '/user/userSocialAccounts/' + id,
    method: 'get'
  })
}

// 新增用户社交账号管理
export function addUserSocialAccounts(data) {
  return request({
    url: '/user/userSocialAccounts',
    method: 'post',
    data: data
  })
}

// 修改用户社交账号管理
export function updateUserSocialAccounts(data) {
  return request({
    url: '/user/userSocialAccounts',
    method: 'put',
    data: data
  })
}

// 删除用户社交账号管理
export function delUserSocialAccounts(id) {
  return request({
    url: '/user/userSocialAccounts/' + id,
    method: 'delete'
  })
}
