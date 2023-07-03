import request from '@/utils/request'

// 查询课程安排
export function planList(query) {
  return request({
    url: '/course/plan/list',
    method: 'get',
    params: query
  })
}
