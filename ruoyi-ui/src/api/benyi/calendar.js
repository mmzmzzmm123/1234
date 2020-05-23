import request from '@/utils/request'

// 查询园历管理(本一)列表
export function listCalendar(query) {
  return request({
    url: '/benyi/calendar/list',
    method: 'get',
    params: query
  })
}

// 查询园历管理(本一)详细
export function getCalendar(id) {
  return request({
    url: '/benyi/calendar/' + id,
    method: 'get'
  })
}

// 新增园历管理(本一)
export function addCalendar(data) {
  return request({
    url: '/benyi/calendar',
    method: 'post',
    data: data
  })
}

// 修改园历管理(本一)
export function updateCalendar(data) {
  return request({
    url: '/benyi/calendar',
    method: 'put',
    data: data
  })
}

// 删除园历管理(本一)
export function delCalendar(id) {
  return request({
    url: '/benyi/calendar/' + id,
    method: 'delete'
  })
}

// 导出园历管理(本一)
export function exportCalendar(query) {
  return request({
    url: '/benyi/calendar/export',
    method: 'get',
    params: query
  })
}

// 查询园历列表
export function getAllSchoolCalendars(query) {
  return request({
    url: '/benyi/calendar/getAllSchoolCalendars',
    method: 'get',
    params: query
  })
}