import request from '@/utils/request'

// 查询科研课题(干部管理)列表
export function listGbkykt(query) {
  return request({
    url: '/gbxxgl/gbkykt/list',
    method: 'get',
    params: query
  })
}

// 查询科研课题(干部管理)详细
export function getGbkykt(id) {
  return request({
    url: '/gbxxgl/gbkykt/' + id,
    method: 'get'
  })
}

// 新增科研课题(干部管理)
export function addGbkykt(data) {
  return request({
    url: '/gbxxgl/gbkykt',
    method: 'post',
    data: data
  })
}

// 修改科研课题(干部管理)
export function updateGbkykt(data) {
  return request({
    url: '/gbxxgl/gbkykt',
    method: 'put',
    data: data
  })
}

// 删除科研课题(干部管理)
export function delGbkykt(id) {
  return request({
    url: '/gbxxgl/gbkykt/' + id,
    method: 'delete'
  })
}

// 导出科研课题(干部管理)
export function exportGbkykt(query) {
  return request({
    url: '/gbxxgl/gbkykt/export',
    method: 'get',
    params: query
  })
}
