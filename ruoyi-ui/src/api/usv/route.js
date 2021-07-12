import request from '@/utils/request'

// 查询计划轨迹列表
export function listRoute(query) {
  return request({
    url: '/usv/route/list',
    method: 'get',
    params: query
  })
}

// 查询计划轨迹详细
export function getRoute(routeId) {
  return request({
    url: '/usv/route/' + routeId,
    method: 'get'
  })
}

// 新增计划轨迹
export function addRoute(data) {
  return request({
    url: '/usv/route',
    method: 'post',
    data: data
  })
}

// 修改计划轨迹
export function updateRoute(data) {
  return request({
    url: '/usv/route',
    method: 'put',
    data: data
  })
}

// 删除计划轨迹
export function delRoute(routeId) {
  return request({
    url: '/usv/route/' + routeId,
    method: 'delete'
  })
}

// 导出计划轨迹
export function exportRoute(query) {
  return request({
    url: '/usv/route/export',
    method: 'get',
    params: query
  })
}