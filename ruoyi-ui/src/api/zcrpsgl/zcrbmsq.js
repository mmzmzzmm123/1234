import request from '@/utils/request'

// 查询主持人报名申请列表
export function listZcrbmsq(query) {
  return request({
    url: '/zcrpsgl/zcrbmsq/list',
    method: 'get',
    params: query
  })
}

// 查询主持人报名申请详细
export function getZcrbmsq(id) {
  return request({
    url: '/zcrpsgl/zcrbmsq/' + id,
    method: 'get'
  })
}

// 新增主持人报名申请
export function addZcrbmsq(data) {
  return request({
    url: '/zcrpsgl/zcrbmsq',
    method: 'post',
    data: data
  })
}

// 修改主持人报名申请
export function updateZcrbmsq(data) {
  return request({
    url: '/zcrpsgl/zcrbmsq',
    method: 'put',
    data: data
  })
}

// 删除主持人报名申请
export function delZcrbmsq(id) {
  return request({
    url: '/zcrpsgl/zcrbmsq/' + id,
    method: 'delete'
  })
}

// 导出主持人报名申请
export function exportZcrbmsq(query) {
  return request({
    url: '/zcrpsgl/zcrbmsq/export',
    method: 'get',
    params: query
  })
}