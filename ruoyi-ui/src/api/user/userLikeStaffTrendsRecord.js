import request from '@/utils/request'

// 查询点赞动态记录列表
export function listUserLikeStaffTrendsRecord(query) {
  return request({
    url: '/user/userLikeStaffTrendsRecord/list',
    method: 'get',
    params: query
  })
}

// 查询点赞动态记录详细
export function getUserLikeStaffTrendsRecord(id) {
  return request({
    url: '/user/userLikeStaffTrendsRecord/' + id,
    method: 'get'
  })
}

// 新增点赞动态记录
export function addUserLikeStaffTrendsRecord(data) {
  return request({
    url: '/user/userLikeStaffTrendsRecord',
    method: 'post',
    data: data
  })
}

// 修改点赞动态记录
export function updateUserLikeStaffTrendsRecord(data) {
  return request({
    url: '/user/userLikeStaffTrendsRecord',
    method: 'put',
    data: data
  })
}

// 删除点赞动态记录
export function delUserLikeStaffTrendsRecord(id) {
  return request({
    url: '/user/userLikeStaffTrendsRecord/' + id,
    method: 'delete'
  })
}
