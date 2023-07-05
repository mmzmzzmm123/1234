import request from '@/utils/request'

// 查询课程安排
export function planList(query) {
  return request({
    url: '/course/plan/list',
    method: 'get',
    params: query
  })
}

// 查询每个班每门课课程安排
export function singleList(query) {
  return request({
    url: '/course/plan/singleList',
    method: 'get',
    params: query
  })
}

// 批量添加课程安排
export function addPlans(query) {
  return request({
    url: '/course/plan/adds',
    method: 'post',
    data: query
  })
}

// 批量删除课程安排
export function delPlans(query) {
  return request({
    url: '/course/plan/dels',
    method: 'post',
    data: query
  })
}
// 成绩管理页面查询每个班每门课课程安排
export function scorePlanList(query) {
  return request({
    url: '/course/plan/scorePlanList',
    method: 'get',
    params: query
  })
}
