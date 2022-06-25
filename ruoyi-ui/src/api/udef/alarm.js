import request from '@/utils/request'

// 查询报警列表
export function listAlarm(query) {
  return request({
    url: '/udef/alarm/list',
    method: 'get',
    params: query
  })
}

// 查询报警详细
export function getAlarm(id) {
  return request({
    url: '/udef/alarm/' + id,
    method: 'get'
  })
}

// 新增报警
export function addAlarm(data) {
  return request({
    url: '/udef/alarm',
    method: 'post',
    data: data
  })
}

// 修改报警
export function updateAlarm(data) {
  return request({
    url: '/udef/alarm',
    method: 'put',
    data: data
  })
}

// 删除报警
export function delAlarm(id) {
  return request({
    url: '/udef/alarm/' + id,
    method: 'delete'
  })
}
