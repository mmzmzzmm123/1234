import request from '@/utils/request'

// 查询学生历史状态列表
export function listHis(query) {
  return request({
    url: '/student/his/list',
    method: 'get',
    params: query
  })
}

// 查询学生历史状态详细
export function getHis(id) {
  return request({
    url: '/student/his/' + id,
    method: 'get'
  })
}

// 新增学生历史状态
export function addHis(data) {
  return request({
    url: '/student/his',
    method: 'post',
    data: data
  })
}

// 修改学生历史状态
export function updateHis(data) {
  return request({
    url: '/student/his',
    method: 'put',
    data: data
  })
}

// 删除学生历史状态
export function delHis(id) {
  return request({
    url: '/student/his/' + id,
    method: 'delete'
  })
}
