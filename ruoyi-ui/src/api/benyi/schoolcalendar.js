import request from '@/utils/request'

// 查询园历管理列表
export function listSchoolcalendar(query) {
  return request({
    url: '/benyi/schoolcalendar/list',
    method: 'get',
    params: query
  })
}

// 查询园历列表
export function getSchoolCalendars(query) {
  return request({
    url: '/benyi/schoolcalendar/getSchoolCalendars',
    method: 'get',
    params: query
  })
}

// 查询园历管理详细
export function getSchoolcalendar(id) {
  return request({
    url: '/benyi/schoolcalendar/' + id,
    method: 'get'
  })
}

// 新增园历管理
export function addSchoolcalendar(data) {
  return request({
    url: '/benyi/schoolcalendar',
    method: 'post',
    data: data
  })
}

// 修改园历管理
export function updateSchoolcalendar(data) {
  return request({
    url: '/benyi/schoolcalendar',
    method: 'put',
    data: data
  })
}

// 删除园历管理
export function delSchoolcalendar(id) {
  return request({
    url: '/benyi/schoolcalendar/' + id,
    method: 'delete'
  })
}

// 导出园历管理
export function exportSchoolcalendar(query) {
  return request({
    url: '/benyi/schoolcalendar/export',
    method: 'get',
    params: query
  })
}