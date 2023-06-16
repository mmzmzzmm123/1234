import request from '@/utils/request'

// 查询预约取消设置列表
export function listRoomcancel(query) {
  return request({
    url: '/office/roomcancel/list',
    method: 'get',
    params: query
  })
}

// 查询预约取消设置详细
export function getRoomcancel(id) {
  return request({
    url: '/office/roomcancel/' + id,
    method: 'get'
  })
}

// 新增预约取消设置
export function addRoomcancel(data) {
  return request({
    url: '/office/roomcancel',
    method: 'post',
    data: data
  })
}

// 修改预约取消设置
export function updateRoomcancel(data) {
  return request({
    url: '/office/roomcancel',
    method: 'put',
    data: data
  })
}

// 删除预约取消设置
export function delRoomcancel(id) {
  return request({
    url: '/office/roomcancel/' + id,
    method: 'delete'
  })
}
