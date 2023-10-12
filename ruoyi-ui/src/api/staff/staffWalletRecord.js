import request from '@/utils/request'

// 查询钱包佣金记录列表
export function listStaffWalletRecord(query) {
  return request({
    url: '/staff/staffWalletRecord/list',
    method: 'get',
    params: query
  })
}

// 查询钱包佣金记录详细
export function getStaffWalletRecord(id) {
  return request({
    url: '/staff/staffWalletRecord/' + id,
    method: 'get'
  })
}

// 新增钱包佣金记录
export function addStaffWalletRecord(data) {
  return request({
    url: '/staff/staffWalletRecord',
    method: 'post',
    data: data
  })
}

// 修改钱包佣金记录
export function updateStaffWalletRecord(data) {
  return request({
    url: '/staff/staffWalletRecord',
    method: 'put',
    data: data
  })
}

// 删除钱包佣金记录
export function delStaffWalletRecord(id) {
  return request({
    url: '/staff/staffWalletRecord/' + id,
    method: 'delete'
  })
}
