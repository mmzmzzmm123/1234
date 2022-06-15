import request from '@/utils/request'

// 查询机器人订单列表
export function listPosition(query) {
  return request({
    url: '/udef/position/list',
    method: 'get',
    params: query
  })
}

// 查询机器人订单详细
export function getPosition(id) {
  return request({
    url: '/udef/position/' + id,
    method: 'get'
  })
}

// 新增机器人订单
export function addPosition(data) {
  return request({
    url: '/udef/position',
    method: 'post',
    data: data
  })
}

// 修改机器人订单
export function updatePosition(data) {
  return request({
    url: '/udef/position',
    method: 'put',
    data: data
  })
}

// 删除机器人订单
export function delPosition(id) {
  return request({
    url: '/udef/position/' + id,
    method: 'delete'
  })
}
