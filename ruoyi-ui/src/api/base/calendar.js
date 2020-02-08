import request from '@/utils/request'

// 查询考勤日历列表
export function listCalendar(query) {
  return request({
    url: '/base/calendar/list',
    method: 'get',
    params: query
  })
}

// 查询考勤日历详细
export function getCalendar(id) {
  return request({
    url: '/base/calendar/' + id,
    method: 'get'
  })
}

// 新增考勤日历
export function addCalendar(data) {
  return request({
    url: '/base/calendar',
    method: 'post',
    data: data
  })
}

// 修改考勤日历
export function updateCalendar(data) {
  return request({
    url: '/base/calendar',
    method: 'put',
    data: data
  })
}

// 删除考勤日历
export function delCalendar(id) {
  return request({
    url: '/base/calendar/' + id,
    method: 'delete'
  })
}

// 导出考勤日历
export function exportCalendar(query) {
  return request({
    url: '/base/calendar/export',
    method: 'get',
    params: query
  })
}