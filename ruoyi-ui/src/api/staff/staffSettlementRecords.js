import request from '@/utils/request'

// 查询员工结算记录列表
export function listStaffSettlementRecords(query) {
  return request({
    url: '/staff/staffSettlementRecords/list',
    method: 'get',
    params: query
  })
}

// 查询员工结算记录详细
export function getStaffSettlementRecords(id) {
  return request({
    url: '/staff/staffSettlementRecords/' + id,
    method: 'get'
  })
}

// 新增员工结算记录
export function addStaffSettlementRecords(data) {
  return request({
    url: '/staff/staffSettlementRecords',
    method: 'post',
    data: data
  })
}

// 修改员工结算记录
export function updateStaffSettlementRecords(data) {
  return request({
    url: '/staff/staffSettlementRecords',
    method: 'put',
    data: data
  })
}

// 删除员工结算记录
export function delStaffSettlementRecords(id) {
  return request({
    url: '/staff/staffSettlementRecords/' + id,
    method: 'delete'
  })
}
