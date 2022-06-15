import request from '@/utils/request'

// 查询机器人订单列表
export function listOrder(query) {
  return request({
    url: '/udef/order/list',
    method: 'get',
    params: query
  })
}

// 查询机器人订单详细
export function getOrder(id) {
  return request({
    url: '/udef/order/' + id,
    method: 'get'
  })
}

// 新增机器人订单
export function addOrder(data) {
  return request({
    url: '/udef/order',
    method: 'post',
    data: data
  })
}

// 修改机器人订单
export function updateOrder(data) {
  return request({
    url: '/udef/order',
    method: 'put',
    data: data
  })
}

// 删除机器人订单
export function delOrder(id) {
  return request({
    url: '/udef/order/' + id,
    method: 'delete'
  })
}
