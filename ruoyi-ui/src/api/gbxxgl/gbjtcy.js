import request from '@/utils/request'

// 查询家庭成员(干部管理-个人状况)列表
export function listGbjtcy(query) {
  return request({
    url: '/gbxxgl/gbjtcy/list',
    method: 'get',
    params: query
  })
}

// 查询家庭成员(干部管理-个人状况)详细
export function getGbjtcy(id) {
  return request({
    url: '/gbxxgl/gbjtcy/' + id,
    method: 'get'
  })
}

// 新增家庭成员(干部管理-个人状况)
export function addGbjtcy(data) {
  return request({
    url: '/gbxxgl/gbjtcy',
    method: 'post',
    data: data
  })
}

// 修改家庭成员(干部管理-个人状况)
export function updateGbjtcy(data) {
  return request({
    url: '/gbxxgl/gbjtcy',
    method: 'put',
    data: data
  })
}

// 删除家庭成员(干部管理-个人状况)
export function delGbjtcy(id) {
  return request({
    url: '/gbxxgl/gbjtcy/' + id,
    method: 'delete'
  })
}

// 导出家庭成员(干部管理-个人状况)
export function exportGbjtcy(query) {
  return request({
    url: '/system/gbjtcy/export',
    method: 'get',
    params: query
  })
}
