import request from '@/utils/request'

// 查询员工礼物记录列表
export function listStaffGiftRecord(query) {
  return request({
    url: '/staff/staffGiftRecord/list',
    method: 'get',
    params: query
  })
}

// 查询员工礼物记录详细
export function getStaffGiftRecord(id) {
  return request({
    url: '/staff/staffGiftRecord/' + id,
    method: 'get'
  })
}

// 新增员工礼物记录
export function addStaffGiftRecord(data) {
  return request({
    url: '/staff/staffGiftRecord',
    method: 'post',
    data: data
  })
}

// 修改员工礼物记录
export function updateStaffGiftRecord(data) {
  return request({
    url: '/staff/staffGiftRecord',
    method: 'put',
    data: data
  })
}

// 删除员工礼物记录
export function delStaffGiftRecord(id) {
  return request({
    url: '/staff/staffGiftRecord/' + id,
    method: 'delete'
  })
}
