import request from '@/utils/request'

// 查询订单列表
export function listOrderManage(query) {
  return request({
    url: '/order/orderManage/list',
    method: 'get',
    params: query
  })
}

// 查询订单详细
export function getOrderManage(id) {
  return request({
    url: '/order/orderManage/' + id,
    method: 'get'
  })
}

// 新增订单
export function addOrderManage(data) {
  return request({
    url: '/order/orderManage',
    method: 'post',
    data: data
  })
}

// 修改订单
export function updateOrderManage(data) {
  return request({
    url: '/order/orderManage',
    method: 'put',
    data: data
  })
}

// 删除订单
export function delOrderManage(id) {
  return request({
    url: '/order/orderManage/' + id,
    method: 'delete'
  })
}
