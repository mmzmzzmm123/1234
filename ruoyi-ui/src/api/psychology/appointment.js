import request from '@/utils/request'

// 查询咨询师预约列表
export function listAppointment(query) {
  return request({
    url: '/psychology/appointment/list',
    method: 'get',
    params: query
  })
}

// 查询咨询师预约详细
export function getAppointment(id) {
  return request({
    url: '/psychology/appointment/' + id,
    method: 'get'
  })
}

// 新增咨询师预约
export function addAppointment(data) {
  return request({
    url: '/psychology/appointment',
    method: 'post',
    data: data
  })
}

// 修改咨询师预约
export function updateAppointment(data) {
  return request({
    url: '/psychology/appointment',
    method: 'put',
    data: data
  })
}

// 删除咨询师预约
export function delAppointment(id) {
  return request({
    url: '/psychology/appointment/' + id,
    method: 'delete'
  })
}
