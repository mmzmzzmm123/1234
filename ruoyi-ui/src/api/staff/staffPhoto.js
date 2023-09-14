import request from '@/utils/request'

// 查询员工相册列表
export function listStaffPhoto(query) {
  return request({
    url: '/staff/staffPhoto/list',
    method: 'get',
    params: query
  })
}

// 查询员工相册详细
export function getStaffPhoto(id) {
  return request({
    url: '/staff/staffPhoto/' + id,
    method: 'get'
  })
}

// 新增员工相册
export function addStaffPhoto(data) {
  return request({
    url: '/staff/staffPhoto',
    method: 'post',
    data: data
  })
}

// 修改员工相册
export function updateStaffPhoto(data) {
  return request({
    url: '/staff/staffPhoto',
    method: 'put',
    data: data
  })
}

// 删除员工相册
export function delStaffPhoto(id) {
  return request({
    url: '/staff/staffPhoto/' + id,
    method: 'delete'
  })
}
