import request from '@/utils/request'

// 查询房间套餐价格列表
export function listRoompackage(query) {
  return request({
    url: '/office/roompackage/list',
    method: 'get',
    params: query
  })
}

// 查询房间套餐价格详细
export function getRoompackage(id) {
  return request({
    url: '/office/roompackage/' + id,
    method: 'get'
  })
}

// 新增房间套餐价格
export function addRoompackage(data) {
  return request({
    url: '/office/roompackage',
    method: 'post',
    data: data
  })
}

// 修改房间套餐价格
export function updateRoompackage(data) {
  return request({
    url: '/office/roompackage',
    method: 'put',
    data: data
  })
}

// 删除房间套餐价格
export function delRoompackage(id) {
  return request({
    url: '/office/roompackage/' + id,
    method: 'delete'
  })
}
