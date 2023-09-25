import request from '@/utils/request'

// 查询收藏员工记录列表
export function listUserLikeStaffRecord(query) {
  return request({
    url: '/user/userLikeStaffRecord/list',
    method: 'get',
    params: query
  })
}

// 查询收藏员工记录详细
export function getUserLikeStaffRecord(id) {
  return request({
    url: '/user/userLikeStaffRecord/' + id,
    method: 'get'
  })
}

// 新增收藏员工记录
export function addUserLikeStaffRecord(data) {
  return request({
    url: '/user/userLikeStaffRecord',
    method: 'post',
    data: data
  })
}

// 修改收藏员工记录
export function updateUserLikeStaffRecord(data) {
  return request({
    url: '/user/userLikeStaffRecord',
    method: 'put',
    data: data
  })
}

// 删除收藏员工记录
export function delUserLikeStaffRecord(id) {
  return request({
    url: '/user/userLikeStaffRecord/' + id,
    method: 'delete'
  })
}
