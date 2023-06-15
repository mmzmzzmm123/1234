import request from '@/utils/request'

// 查询优惠券 t_store_promotion列表
export function listStorepromotion(query) {
  return request({
    url: '/office/storepromotion/list',
    method: 'get',
    params: query
  })
}

// 查询优惠券 t_store_promotion详细
export function getStorepromotion(id) {
  return request({
    url: '/office/storepromotion/' + id,
    method: 'get'
  })
}

// 新增优惠券 t_store_promotion
export function addStorepromotion(data) {
  return request({
    url: '/office/storepromotion',
    method: 'post',
    data: data
  })
}

// 修改优惠券 t_store_promotion
export function updateStorepromotion(data) {
  return request({
    url: '/office/storepromotion',
    method: 'put',
    data: data
  })
}

// 删除优惠券 t_store_promotion
export function delStorepromotion(id) {
  return request({
    url: '/office/storepromotion/' + id,
    method: 'delete'
  })
}
