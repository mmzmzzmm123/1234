import request from '@/utils/request'

// 查询会员合约列表
export function listContract(query) {
  return request({
    url: '/csa/contract/list',
    method: 'get',
    params: query
  })
}

// 查询入驻会员合约列表
export function listEnterContract(query) {
  return request({
    url: '/csa/contract/enter/list',
    method: 'get',
    params: query
  })
}

// 查询会员合约详细
export function getContract(contractId) {
  return request({
    url: '/csa/contract/' + contractId,
    method: 'get'
  })
}

// 新增会员合约
export function addContract(data) {
  return request({
    url: '/csa/contract',
    method: 'post',
    data: data
  })
}

// 修改会员合约
export function updateContract(data) {
  return request({
    url: '/csa/contract',
    method: 'put',
    data: data
  })
}

// 审核会员合约
export function auditContract(id) {
  return request({
    url: '/csa/contract/audit/' + id,
    method: 'put'
  })
}

// 删除会员合约
export function delContract(contractId) {
  return request({
    url: '/csa/contract/' + contractId,
    method: 'delete'
  })
}
