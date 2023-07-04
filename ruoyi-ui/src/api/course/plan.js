import request from '@/utils/request'

// 查询课程安排
export function planList(query) {
  return request({
    url: '/course/plan/list',
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
