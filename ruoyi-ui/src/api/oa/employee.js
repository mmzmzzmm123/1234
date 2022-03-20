import request from '@/utils/request'

// 查询员工信息列表
export function listEmployee(query) {
  return request({
    url: '/oa/employee/list',
    method: 'get',
    params: query
  })
}

// 查询员工信息详细
export function getEmployee(userId) {
  return request({
    url: '/oa/employee/' + userId,
    method: 'get'
  })
}

// 新增员工信息
export function addEmployee(data) {
  return request({
    url: '/oa/employee',
    method: 'post',
    data: data
  })
}

// 修改员工信息
export function updateEmployee(data) {
  return request({
    url: '/oa/employee',
    method: 'put',
    data: data
  })
}

// 删除员工信息
export function delEmployee(userId) {
  return request({
    url: '/oa/employee/' + userId,
    method: 'delete'
  })
}
