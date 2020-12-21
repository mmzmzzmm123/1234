import request from '@/utils/request'

// 查询荣誉奖励(干部管理)列表
export function listGbryjl(query) {
  return request({
    url: '/gbxxgl/gbryjl/list',
    method: 'get',
    params: query
  })
}

// 查询荣誉奖励(干部管理)详细
export function getGbryjl(id) {
  return request({
    url: '/gbxxgl/gbryjl/' + id,
    method: 'get'
  })
}

// 新增荣誉奖励(干部管理)
export function addGbryjl(data) {
  return request({
    url: '/gbxxgl/gbryjl',
    method: 'post',
    data: data
  })
}

// 修改荣誉奖励(干部管理)
export function updateGbryjl(data) {
  return request({
    url: '/gbxxgl/gbryjl',
    method: 'put',
    data: data
  })
}

// 删除荣誉奖励(干部管理)
export function delGbryjl(id) {
  return request({
    url: '/gbxxgl/gbryjl/' + id,
    method: 'delete'
  })
}

// 导出荣誉奖励(干部管理)
export function exportGbryjl(query) {
  return request({
    url: '/gbxxgl/gbryjl/export',
    method: 'get',
    params: query
  })
}