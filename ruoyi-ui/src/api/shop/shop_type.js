import request from '@/utils/request'

// 查询电商平台列表
export function listShop_type(query) {
  return request({
    url: '/shop/shop_type/list',
    method: 'get',
    params: query
  })
}

// 查询电商平台详细
export function getShop_type(id) {
  return request({
    url: '/shop/shop_type/' + id,
    method: 'get'
  })
}

// 新增电商平台
export function addShop_type(data) {
  return request({
    url: '/shop/shop_type',
    method: 'post',
    data: data
  })
}

// 修改电商平台
export function updateShop_type(data) {
  return request({
    url: '/shop/shop_type',
    method: 'put',
    data: data
  })
}

// 删除电商平台
export function delShop_type(id) {
  return request({
    url: '/shop/shop_type/' + id,
    method: 'delete'
  })
}

// 导出电商平台
export function exportShop_type(query) {
  return request({
    url: '/shop/shop_type/export',
    method: 'get',
    params: query
  })
}