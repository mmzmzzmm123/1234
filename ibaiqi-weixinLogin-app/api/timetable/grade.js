import request from '../../utils/request'
// 查询课程班级管理列表
export function listGrade(query) {
  return request({
    url: '/timetable/grade/list',
    method: 'get',
    params: query
  })
}

// 查询课程班级管理详细
export function getGrade(gradeId) {
  return request({
    url: '/timetable/grade/' + gradeId,
    method: 'get'
  })
}

// 新增课程班级管理
export function addGrade(data) {
  return request({
    url: '/timetable/grade',
    method: 'post',
    data: data
  })
}

// 修改课程班级管理
export function updateGrade(data) {
  return request({
    url: '/timetable/grade',
    method: 'put',
    data: data
  })
}

// 删除课程班级管理
export function delGrade(gradeId) {
  return request({
    url: '/timetable/grade/' + gradeId,
    method: 'delete'
  })
}
