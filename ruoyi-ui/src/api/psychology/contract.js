import request from '@/utils/request'

// 查询咨询师合同协议列表
export function listContract(query) {
  return request({
    url: '/psychology/contract/list',
    method: 'get',
    params: query
  })
}

export function getListByUserName() {
  return request({
    url: '/psychology/contract/getListByUserName',
    method: 'get',
  })
}

export function exist(query) {
  return request({
    url: '/psychology/contract/exist',
    method: 'get',
    params: query
  })
}

// 查询咨询师合同协议详细
export function getContract(id) {
  return request({
    url: '/psychology/contract/' + id,
    method: 'get'
  })
}

// 新增咨询师合同协议
export function addContract(data) {
  return request({
    url: '/psychology/contract',
    method: 'post',
    data: data
  })
}

// 修改咨询师合同协议
export function updateContract(data) {
  return request({
    url: '/psychology/contract',
    method: 'put',
    data: data
  })
}

// 重新发布合同
export function relaunch(data) {
  return request({
    url: '/psychology/contract/relaunch',
    method: 'post',
    data: data
  })
}

// 删除咨询师合同协议
export function delContract(id) {
  return request({
    url: '/psychology/contract/' + id,
    method: 'delete'
  })
}
