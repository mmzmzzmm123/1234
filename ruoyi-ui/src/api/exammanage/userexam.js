import request from '@/utils/request'

// 查询考试记录列表
export function listUserexam(query) {
  return request({
    url: '/exammanage/userexam/list',
    method: 'get',
    params: query
  })
}

// 查询考试记录详细
export function getUserexam(id) {
  return request({
    url: '/exammanage/userexam/' + id,
    method: 'get'
  })
}

// 新增考试记录
export function addUserexam(data) {
  return request({
    url: '/exammanage/userexam',
    method: 'post',
    data: data
  })
}

// 修改考试记录
export function updateUserexam(data) {
  return request({
    url: '/exammanage/userexam',
    method: 'put',
    data: data
  })
}

// 删除考试记录
export function delUserexam(id) {
  return request({
    url: '/exammanage/userexam/' + id,
    method: 'delete'
  })
}
