import request from '@/utils/request'

// 查询汽车品牌管理列表
export function listBrand(query) {
  return request({
    url: '/system/brand/list',
    method: 'get',
    params: query
  })
}

// 查询汽车品牌管理详细
export function getBrand(id) {
  return request({
    url: '/system/brand/' + id,
    method: 'get'
  })
}

// 新增汽车品牌管理
export function addBrand(data) {
  return request({
    url: '/system/brand',
    method: 'post',
    data: data
  })
}

// 修改汽车品牌管理
export function updateBrand(data) {
  return request({
    url: '/system/brand',
    method: 'put',
    data: data
  })
}

// 删除汽车品牌管理
export function delBrand(id) {
  return request({
    url: '/system/brand/' + id,
    method: 'delete'
  })
}
