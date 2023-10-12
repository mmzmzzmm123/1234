import request from '@/utils/request'

// 查询订单评论管理列表
export function listOrderComment(query) {
  return request({
    url: '/order/orderComment/list',
    method: 'get',
    params: query
  })
}

// 查询订单评论管理详细
export function getOrderComment(id) {
  return request({
    url: '/order/orderComment/' + id,
    method: 'get'
  })
}

// 新增订单评论管理
export function addOrderComment(data) {
  return request({
    url: '/order/orderComment',
    method: 'post',
    data: data
  })
}

// 修改订单评论管理
export function updateOrderComment(data) {
  return request({
    url: '/order/orderComment',
    method: 'put',
    data: data
  })
}

// 删除订单评论管理
export function delOrderComment(id) {
  return request({
    url: '/order/orderComment/' + id,
    method: 'delete'
  })
}
