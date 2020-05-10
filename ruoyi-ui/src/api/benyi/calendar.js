import request from '@/utils/request'

// 查询园历管理(本一)列表
export function listCalendar(query) {
  return request({
    url: '/system/calendar/list',
    method: 'get',
    params: query
  })
}

// 查询园历管理(本一)详细
export function getCalendar(id) {
  return request({
    url: '/system/calendar/' + id,
    method: 'get'
  })
}

// 新增园历管理(本一)
export function addCalendar(data) {
  return request({
    url: '/system/calendar',
    method: 'post',
    data: data
  })
}

// 修改园历管理(本一)
export function updateCalendar(data) {
  return request({
    url: '/system/calendar',
    method: 'put',
    data: data
  })
}

// 删除园历管理(本一)
export function delCalendar(id) {
  return request({
    url: '/system/calendar/' + id,
    method: 'delete'
  })
}

// 导出园历管理(本一)
export function exportCalendar(query) {
  return request({
    url: '/system/calendar/export',
    method: 'get',
    params: query
  })
}