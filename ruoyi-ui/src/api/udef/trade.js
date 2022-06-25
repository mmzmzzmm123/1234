import request from '@/utils/request'

// 查询成交订单列表
export function listTrade(query) {
  return request({
    url: '/udef/trade/list',
    method: 'get',
    params: query
  })
}

// 查询成交订单详细
export function getTrade(id) {
  return request({
    url: '/udef/trade/' + id,
    method: 'get'
  })
}

// 新增成交订单
export function addTrade(data) {
  return request({
    url: '/udef/trade',
    method: 'post',
    data: data
  })
}

// 修改成交订单
export function updateTrade(data) {
  return request({
    url: '/udef/trade',
    method: 'put',
    data: data
  })
}

// 删除成交订单
export function delTrade(id) {
  return request({
    url: '/udef/trade/' + id,
    method: 'delete'
  })
}
