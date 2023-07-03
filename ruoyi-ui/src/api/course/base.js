import request from '@/utils/request'

// 查询课程
export function courseList(query) {
  return request({
    url: '/course/base/list',
    method: 'get',
    params: query
  })
}

// 查询所有课程
export function allCourse(query) {
  return request({
    url: '/course/base/all',
    method: 'get',
    params: query
  })
}
// 修改课程信息
export function updateCourse(query) {
  return request({
    url: '/course/base/update',
    method: 'post',
    data: query
  })
}

// 添加课程信息
export function addCourse(query) {
  return request({
    url: '/course/base/add',
    method: 'post',
    data: query
  })
}

// 删除课程信息
export function delCourse(query) {
  return request({
    url: '/course/base/del',
    method: 'post',
    data: query
  })
}
