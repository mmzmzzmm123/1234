import request from '@/utils/request'

// 查询房间续费套餐列表
export function listRoomchargeprice(query) {
  return request({
    url: '/office/roomchargeprice/list',
    method: 'get',
    params: query
  })
}

// 查询房间续费套餐详细
export function getRoomchargeprice(id) {
  return request({
    url: '/office/roomchargeprice/' + id,
    method: 'get'
  })
}

// 新增房间续费套餐
export function addRoomchargeprice(data) {
  return request({
    url: '/office/roomchargeprice',
    method: 'post',
    data: data
  })
}

// 修改房间续费套餐
export function updateRoomchargeprice(data) {
  return request({
    url: '/office/roomchargeprice',
    method: 'put',
    data: data
  })
}

// 删除房间续费套餐
export function delRoomchargeprice(id) {
  return request({
    url: '/office/roomchargeprice/' + id,
    method: 'delete'
  })
}
