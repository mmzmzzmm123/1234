import request from '@/utils/request'

// 查询店铺充值套餐列表
export function listStorepackage(query) {
  return request({
    url: '/office/storepackage/list',
    method: 'get',
    params: query
  })
}

// 查询店铺充值套餐详细
export function getStorepackage(id) {
  return request({
    url: '/office/storepackage/' + id,
    method: 'get'
  })
}

// 新增店铺充值套餐
export function addStorepackage(data) {
  return request({
    url: '/office/storepackage',
    method: 'post',
    data: data
  })
}

// 修改店铺充值套餐
export function updateStorepackage(data) {
  return request({
    url: '/office/storepackage',
    method: 'put',
    data: data
  })
}

// 删除店铺充值套餐
export function delStorepackage(id) {
  return request({
    url: '/office/storepackage/' + id,
    method: 'delete'
  })
}
