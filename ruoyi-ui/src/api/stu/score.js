import request from '@/utils/request'

// 查询课程计划信息列表
export function listInfo(query) {
  return request({
    url: '/stu/score/list',
    method: 'get',
    params: query
  })
}

// 查询某班某门课程成绩列表
export function scoreInfo(query) {
  return request({
    url: '/stu/score/courseScore',
    method: 'get',
    params: query
  })
}
// 查询某班学生平均成绩列表
export function hisScoure(query) {
  return request({
    url: '/stu/score/hisScoure',
    method: 'get',
    params: query
  })
}
// 设置某班学生成绩
export function setScore(query) {
  return request({
    url: '/stu/score/setScore',
    method: 'post',
    data: query
  })
}
