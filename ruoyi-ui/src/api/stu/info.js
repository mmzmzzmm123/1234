import request from '@/utils/request'

// 查询学生信息列表
export function listInfo(query) {
  return request({
    url: '/stu/info/list',
    method: 'get',
    params: query
  })
}

// 查询学生信息详细
export function getInfo(stuId) {
  return request({
    url: '/stu/info/' + stuId,
    method: 'get'
  })
}

// 新增学生信息
export function addInfo(data) {
  return request({
    url: '/stu/info',
    method: 'post',
    data: data
  })
}

// 修改学生信息
export function updateInfo(data) {
  return request({
    url: '/stu/info',
    method: 'put',
    data: data
  })
}

// 删除学生信息
export function delInfo(stuId) {
  return request({
    url: '/stu/info/' + stuId,
    method: 'delete'
  })
}
