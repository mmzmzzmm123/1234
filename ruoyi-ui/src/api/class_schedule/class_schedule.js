import request from '@/utils/request'

// 查询老师_班级_课程列表
export function listClass_schedule(query) {
  return request({
    url: '/class_schedule/class_schedule/list',
    method: 'get',
    params: query
  })
}

// 查询老师_班级_课程详细
export function getClass_schedule(id) {
  return request({
    url: '/class_schedule/class_schedule/' + id,
    method: 'get'
  })
}

// 新增老师_班级_课程
export function addClass_schedule(data) {
  return request({
    url: '/class_schedule/class_schedule',
    method: 'post',
    data: data
  })
}

// 修改老师_班级_课程
export function updateClass_schedule(data) {
  return request({
    url: '/class_schedule/class_schedule',
    method: 'put',
    data: data
  })
}

// 删除老师_班级_课程
export function delClass_schedule(id) {
  return request({
    url: '/class_schedule/class_schedule/' + id,
    method: 'delete'
  })
}
