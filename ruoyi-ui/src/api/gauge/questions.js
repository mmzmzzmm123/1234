import request from '@/utils/request'

// 查询心理测评问题列表
export function listQuestions(query) {
  return request({
    url: '/gauge/questions/list',
    method: 'get',
    params: query
  })
}

// 查询心理测评问题详细
export function getQuestions(id) {
  return request({
    url: '/gauge/questions/' + id,
    method: 'get'
  })
}

// 新增心理测评问题
export function addQuestions(data) {
  return request({
    url: '/gauge/questions',
    method: 'post',
    data: data
  })
}

// 修改心理测评问题
export function updateQuestions(data) {
  return request({
    url: '/gauge/questions',
    method: 'put',
    data: data
  })
}

// 删除心理测评问题
export function delQuestions(id) {
  return request({
    url: '/gauge/questions/' + id,
    method: 'delete'
  })
}
