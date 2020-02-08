import request from '@/utils/request'

// 查询考勤日历明细列表
export function listCalendarItem(query) {
  return request({
    url: '/base/calendarItem/list',
    method: 'get',
    params: query
  })
}

// 查询考勤日历明细详细
export function getCalendarItem(id) {
  return request({
    url: '/base/calendarItem/' + id,
    method: 'get'
  })
}

// 新增考勤日历明细
export function addCalendarItem(data) {
  return request({
    url: '/base/calendarItem',
    method: 'post',
    data: data
  })
}

// 修改考勤日历明细
export function updateCalendarItem(data) {
  return request({
    url: '/base/calendarItem',
    method: 'put',
    data: data
  })
}

// 删除考勤日历明细
export function delCalendarItem(id) {
  return request({
    url: '/base/calendarItem/' + id,
    method: 'delete'
  })
}

// 导出考勤日历明细
export function exportCalendarItem(query) {
  return request({
    url: '/base/calendarItem/export',
    method: 'get',
    params: query
  })
}