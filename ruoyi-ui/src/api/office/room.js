import request from '@/utils/request'

// 查询店铺房间列表
export function listRoom(query) {
  return request({
    url: '/office/room/list',
    method: 'get',
    params: query
  })
}

// 查询店铺房间详细
export function getRoom(id) {
  return request({
    url: '/office/room/' + id,
    method: 'get'
  })
}

// 新增店铺房间
export function addRoom(data) {
  return request({
    url: '/office/room',
    method: 'post',
    data: data
  })
}

// 修改店铺房间
export function updateRoom(data) {
  return request({
    url: '/office/room',
    method: 'put',
    data: data
  })
}

// 删除店铺房间
export function delRoom(id) {
  return request({
    url: '/office/room/' + id,
    method: 'delete'
  })
}
