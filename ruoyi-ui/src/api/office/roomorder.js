import request from '@/utils/request'

// 查询房间占用（点支付时再次校验可用性并改变状态，支付失败回滚）列表
export function listRoomorder(query) {
  return request({
    url: '/office/roomorder/list',
    method: 'get',
    params: query
  })
}

// 查询房间占用（点支付时再次校验可用性并改变状态，支付失败回滚）详细
export function getRoomorder(id) {
  return request({
    url: '/office/roomorder/' + id,
    method: 'get'
  })
}

// 新增房间占用（点支付时再次校验可用性并改变状态，支付失败回滚）
export function addRoomorder(data) {
  return request({
    url: '/office/roomorder',
    method: 'post',
    data: data
  })
}

// 修改房间占用（点支付时再次校验可用性并改变状态，支付失败回滚）
export function updateRoomorder(data) {
  return request({
    url: '/office/roomorder',
    method: 'put',
    data: data
  })
}

// 删除房间占用（点支付时再次校验可用性并改变状态，支付失败回滚）
export function delRoomorder(id) {
  return request({
    url: '/office/roomorder/' + id,
    method: 'delete'
  })
}
