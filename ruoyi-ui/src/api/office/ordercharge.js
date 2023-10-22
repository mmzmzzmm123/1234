import request from '@/utils/request'

// 查询续费订单列表
export function listOrdercharge(query) {
  return request({
    url: '/office/ordercharge/list',
    method: 'get',
    params: query
  })
}

// 查询续费订单详细
export function getOrdercharge(id) {
  return request({
    url: '/office/ordercharge/' + id,
    method: 'get'
  })
}

// 新增续费订单
export function addOrdercharge(data) {
  return request({
    url: '/office/ordercharge',
    method: 'post',
    data: data
  })
}

// 修改续费订单
export function updateOrdercharge(data) {
  return request({
    url: '/office/ordercharge',
    method: 'put',
    data: data
  })
}

// 删除续费订单
export function delOrdercharge(id) {
  return request({
    url: '/office/ordercharge/' + id,
    method: 'delete'
  })
}
