import request from '@/utils/request'

// 查询计划列表
export function listSchedule(query) {
  return request({
    url: '/udef/schedule/list',
    method: 'get',
    params: query
  })
}

// 查询计划详细
export function getSchedule(id) {
  return request({
    url: '/udef/schedule/' + id,
    method: 'get'
  })
}

// 新增计划
export function addSchedule(data) {
  return request({
    url: '/udef/schedule',
    method: 'post',
    data: data
  })
}

// 修改计划
export function updateSchedule(data) {
  return request({
    url: '/udef/schedule',
    method: 'put',
    data: data
  })
}

// 删除计划
export function delSchedule(id) {
  return request({
    url: '/udef/schedule/' + id,
    method: 'delete'
  })
}
