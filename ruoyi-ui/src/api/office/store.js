import request from '@/utils/request'

// 查询商家用户店铺列表
export function listStore(query) {
  return request({
    url: '/office/store/list',
    method: 'get',
    params: query
  })
}

// 查询商家用户店铺详细
export function getStore(id) {
  return request({
    url: '/office/store/' + id,
    method: 'get'
  })
}

// 新增商家用户店铺
export function addStore(data) {
  return request({
    url: '/office/store',
    method: 'post',
    data: data
  })
}

// 修改商家用户店铺
export function updateStore(data) {
  return request({
    url: '/office/store',
    method: 'put',
    data: data
  })
}

// 删除商家用户店铺
export function delStore(id) {
  return request({
    url: '/office/store/' + id,
    method: 'delete'
  })
}
