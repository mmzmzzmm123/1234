import request from '@/utils/request'

// 查询用户订阅产品列表
export function listUserproduct(query) {
  return request({
    url: '/system/userproduct/list',
    method: 'get',
    params: query
  })
}

// 查询用户订阅产品详细
export function getUserproduct(id) {
  return request({
    url: '/system/userproduct/' + id,
    method: 'get'
  })
}

// 新增用户订阅产品
export function addUserproduct(data) {
  return request({
    url: '/system/userproduct',
    method: 'post',
    data: data
  })
}

// 修改用户订阅产品
export function updateUserproduct(data) {
  return request({
    url: '/system/userproduct',
    method: 'put',
    data: data
  })
}

// 删除用户订阅产品
export function delUserproduct(id) {
  return request({
    url: '/system/userproduct/' + id,
    method: 'delete'
  })
}
