import request from '@/utils/request'

// 查询转介订单列表
export function listOrder(query) {
  return request({
    url: '/system/order/list',
    method: 'get',
    params: query
  })
}

// 查询转介订单详细
export function getOrder(referralId) {
  return request({
    url: '/system/order/' + referralId,
    method: 'get'
  })
}

// 新增转介订单
export function addOrder(data) {
  return request({
    url: '/system/order',
    method: 'post',
    data: data
  })
}

// 修改转介订单
export function updateOrder(data) {
  return request({
    url: '/system/order',
    method: 'put',
    data: data
  })
}

// 删除转介订单
export function delOrder(referralId) {
  return request({
    url: '/system/order/' + referralId,
    method: 'delete'
  })
}
