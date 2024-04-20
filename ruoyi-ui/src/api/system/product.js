import request from '@/utils/request'

// 查询商品列表
export function listProduct(query) {
  return request({
    url: '/system/product/listvo',
    method: 'get',
    params: query
  })
}

// 查询商品详细
export function getProduct(id) {
  return request({
    url: '/system/product/vo/' + id,
    method: 'get'
  })
}

// 新增商品
export function addProduct(data) {
  return request({
    url: '/system/product/addVo',
    method: 'post',
    data: data
  })
}

// 修改商品
export function updateProduct(data) {
  return request({
    url: '/system/product/editVO',
    method: 'put',
    data: data
  })
}

// 删除商品
export function delProduct(id) {
  return request({
    url: '/system/product/' + id,
    method: 'delete'
  })
}
