import request from '@/utils/request'

// 查询订单服务暂停列表
export function listPause(query) {
  return request({
    url: '/orderPause/pause/list',
    method: 'get',
    params: query
  })
}

// 查询订单服务暂停详细
export function getPause(id) {
  return request({
    url: '/orderPause/pause/' + id,
    method: 'get'
  })
}

// 新增订单服务暂停
export function addPause(data) {
  return request({
    url: '/orderPause/pause',
    method: 'post',
    data: data
  })
}

// 修改订单服务暂停
export function updatePause(data) {
  return request({
    url: '/orderPause/pause',
    method: 'put',
    data: data
  })
}

// 删除订单服务暂停
export function delPause(id) {
  return request({
    url: '/orderPause/pause/' + id,
    method: 'delete'
  })
}

// 导出订单服务暂停
export function exportPause(query) {
  return request({
    url: '/orderPause/pause/export',
    method: 'get',
    params: query
  })
}
