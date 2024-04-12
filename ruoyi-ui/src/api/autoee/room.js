import request from '@/utils/request'

// 查询房间管理列表
export function listRoom(query) {
  return request({
    url: '/autoee/room/list',
    method: 'get',
    params: query
  })
}

// 查询房间管理详细
export function getRoom(id) {
  return request({
    url: '/autoee/room/' + id,
    method: 'get'
  })
}

// 新增房间管理
export function addRoom(data) {
  return request({
    url: '/autoee/room',
    method: 'post',
    data: data
  })
}

// 修改房间管理
export function updateRoom(data) {
  return request({
    url: '/autoee/room',
    method: 'put',
    data: data
  })
}

// 删除房间管理
export function delRoom(id) {
  return request({
    url: '/autoee/room/' + id,
    method: 'delete'
  })
}
