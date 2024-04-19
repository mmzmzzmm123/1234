import request from '@/utils/request'

// 查询商品和分类关联列表
export function listProductcategory(query) {
  return request({
    url: '/system/productcategory/list',
    method: 'get',
    params: query
  })
}

// 查询商品和分类关联详细
export function getProductcategory(id) {
  return request({
    url: '/system/productcategory/' + id,
    method: 'get'
  })
}

// 新增商品和分类关联
export function addProductcategory(data) {
  return request({
    url: '/system/productcategory',
    method: 'post',
    data: data
  })
}

// 修改商品和分类关联
export function updateProductcategory(data) {
  return request({
    url: '/system/productcategory',
    method: 'put',
    data: data
  })
}

// 删除商品和分类关联
export function delProductcategory(id) {
  return request({
    url: '/system/productcategory/' + id,
    method: 'delete'
  })
}
