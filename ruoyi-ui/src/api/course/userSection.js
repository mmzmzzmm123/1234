import request from '@/utils/request'

// 查询用户-课程-章节关系列表
export function listUserSection(query) {
  return request({
    url: '/course/userSection/list',
    method: 'get',
    params: query
  })
}

// 查询用户-课程-章节关系详细
export function getUserSection(id) {
  return request({
    url: '/course/userSection/' + id,
    method: 'get'
  })
}

// 新增用户-课程-章节关系
export function addUserSection(data) {
  return request({
    url: '/course/userSection',
    method: 'post',
    data: data
  })
}

// 修改用户-课程-章节关系
export function updateUserSection(data) {
  return request({
    url: '/course/userSection',
    method: 'put',
    data: data
  })
}

// 删除用户-课程-章节关系
export function delUserSection(id) {
  return request({
    url: '/course/userSection/' + id,
    method: 'delete'
  })
}
