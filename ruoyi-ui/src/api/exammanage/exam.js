import request from '@/utils/request'

// 查询考试管理列表
export function listExam(query) {
  return request({
    url: '/exammange/exam/list',
    method: 'get',
    params: query
  })
}

// 查询考试管理详细
export function getExam(id) {
  return request({
    url: '/exammange/exam/' + id,
    method: 'get'
  })
}

// 新增考试管理
export function addExam(data) {
  return request({
    url: '/exammange/exam',
    method: 'post',
    data: data
  })
}

// 修改考试管理
export function updateExam(data) {
  return request({
    url: '/exammange/exam',
    method: 'put',
    data: data
  })
}

// 删除考试管理
export function delExam(id) {
  return request({
    url: '/exammange/exam/' + id,
    method: 'delete'
  })
}
