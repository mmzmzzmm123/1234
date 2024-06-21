import request from '@/utils/request'

// 查询提现订单列表
export function listOrder(query) {
  return request({
    url: '/withdrawal/order/list',
    method: 'get',
    params: query
  })
}

// 查询提现订单详细
export function getOrder(withdrawalNo) {
  return request({
    url: '/withdrawal/order/' + withdrawalNo,
    method: 'get'
  })
}

// 新增提现订单
export function addOrder(data) {
  return request({
    url: '/withdrawal/order',
    method: 'post',
    data: data
  })
}

// 修改提现订单
export function updateOrder(data) {
  return request({
    url: '/withdrawal/order',
    method: 'put',
    data: data
  })
}

// 删除提现订单
export function delOrder(withdrawalNo) {
  return request({
    url: '/withdrawal/order/' + withdrawalNo,
    method: 'delete'
  })
}
