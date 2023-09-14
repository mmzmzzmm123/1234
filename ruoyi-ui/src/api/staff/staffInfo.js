import request from '@/utils/request'

// 查询员工信息列表
export function listStaffInfo(query) {
  return request({
    url: '/staff/staffInfo/list',
    method: 'get',
    params: query
  })
}

// 查询员工信息详细
export function getStaffInfo(userId) {
  return request({
    url: '/staff/staffInfo/' + userId,
    method: 'get'
  })
}

// 新增员工信息
export function addStaffInfo(data) {
  return request({
    url: '/staff/staffInfo',
    method: 'post',
    data: data
  })
}

// 修改员工信息
export function updateStaffInfo(data) {
  return request({
    url: '/staff/staffInfo',
    method: 'put',
    data: data
  })
}

// 删除员工信息
export function delStaffInfo(userId) {
  return request({
    url: '/staff/staffInfo/' + userId,
    method: 'delete'
  })
}
