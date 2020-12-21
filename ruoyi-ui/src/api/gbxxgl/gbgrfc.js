import request from '@/utils/request'

// 查询个人风采(干部管理-个人状况)列表
export function listGbgrfc(query) {
  return request({
    url: '/gbxxgl/gbgrfc/list',
    method: 'get',
    params: query
  })
}

// 查询个人风采(干部管理-个人状况)详细
export function getGbgrfc(id) {
  return request({
    url: '/gbxxgl/gbgrfc/' + id,
    method: 'get'
  })
}

// 新增个人风采(干部管理-个人状况)
export function addGbgrfc(data) {
  return request({
    url: '/gbxxgl/gbgrfc',
    method: 'post',
    data: data
  })
}

// 修改个人风采(干部管理-个人状况)
export function updateGbgrfc(data) {
  return request({
    url: '/gbxxgl/gbgrfc',
    method: 'put',
    data: data
  })
}

// 删除个人风采(干部管理-个人状况)
export function delGbgrfc(id) {
  return request({
    url: '/gbxxgl/gbgrfc/' + id,
    method: 'delete'
  })
}

// 导出个人风采(干部管理-个人状况)
export function exportGbgrfc(query) {
  return request({
    url: '/gbxxgl/gbgrfc/export',
    method: 'get',
    params: query
  })
}
