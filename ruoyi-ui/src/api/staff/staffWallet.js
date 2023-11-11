import request from '@/utils/request'

// 查询员工钱包列表
export function listStaffWallet(query) {
  return request({
    url: '/staff/staffWallet/list',
    method: 'get',
    params: query
  })
}

// 查询员工钱包详细
export function getStaffWallet(id) {
  return request({
    url: '/staff/staffWallet/' + id,
    method: 'get'
  })
}

// 新增员工钱包
export function addStaffWallet(data) {
  return request({
    url: '/staff/staffWallet',
    method: 'post',
    data: data
  })
}

// 修改员工钱包
export function updateStaffWallet(data) {
  return request({
    url: '/staff/staffWallet',
    method: 'put',
    data: data
  })
}

// 删除员工钱包
export function delStaffWallet(id) {
  return request({
    url: '/staff/staffWallet/' + id,
    method: 'delete'
  })
}
