import request from '@/utils/request'

// 查询商家店铺用户列表
export function listStoreuser(query) {
  return request({
    url: '/office/storeuser/list',
    method: 'get',
    params: query
  })
}

// 查询商家店铺用户详细
export function getStoreuser(id) {
  return request({
    url: '/office/storeuser/' + id,
    method: 'get'
  })
}

// 新增商家店铺用户
export function addStoreuser(data) {
  return request({
    url: '/office/storeuser',
    method: 'post',
    data: data
  })
}

// 修改商家店铺用户
export function updateStoreuser(data) {
  return request({
    url: '/office/storeuser',
    method: 'put',
    data: data
  })
}

// 删除商家店铺用户
export function delStoreuser(id) {
  return request({
    url: '/office/storeuser/' + id,
    method: 'delete'
  })
}
