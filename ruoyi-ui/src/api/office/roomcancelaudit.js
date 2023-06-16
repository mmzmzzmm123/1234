import request from '@/utils/request'

// 查询预约取消审核列表
export function listRoomcancelaudit(query) {
  return request({
    url: '/office/roomcancelaudit/list',
    method: 'get',
    params: query
  })
}

// 查询预约取消审核详细
export function getRoomcancelaudit(id) {
  return request({
    url: '/office/roomcancelaudit/' + id,
    method: 'get'
  })
}

// 新增预约取消审核
export function addRoomcancelaudit(data) {
  return request({
    url: '/office/roomcancelaudit',
    method: 'post',
    data: data
  })
}

// 修改预约取消审核
export function updateRoomcancelaudit(data) {
  return request({
    url: '/office/roomcancelaudit',
    method: 'put',
    data: data
  })
}

// 删除预约取消审核
export function delRoomcancelaudit(id) {
  return request({
    url: '/office/roomcancelaudit/' + id,
    method: 'delete'
  })
}

// 审核通过
export function approve(data) {
  return request({
    url: '/office/roomcancelaudit/approve',
    method: 'put',
    data: data
  })
}



// 审核退回
export function auditBack(data) {
  return request({
    url: '/office/roomcancelaudit/back',
    method: 'put',
    data: data
  })
}
