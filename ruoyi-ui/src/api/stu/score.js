import request from '@/utils/request'

// 查询学生信息列表
export function listInfo(query) {
  return request({
    url: '/stu/score/list',
    method: 'get',
    params: query
  })
}
