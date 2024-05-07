import request from '@/utils/request'

// 查询商品价格列表
export function listPrice(query) {
  return request({
    url: '/system/price/list',
    method: 'get',
    params: query
  })
}

// 查询商品价格详细
export function getPrice(id) {
  return request({
    url: '/system/price/' + id,
    method: 'get'
  })
}

// 新增商品价格
export function addPrice(data) {
  return request({
    url: '/system/price',
    method: 'post',
    data: data
  })
}

// 修改商品价格
export function updatePrice(data) {
  return request({
    url: '/system/price',
    method: 'put',
    data: data
  })
}

// 删除商品价格
export function delPrice(id) {
  return request({
    url: '/system/price/' + id,
    method: 'delete'
  })
}
