import request from '@/utils/request'

// 查询会员签约列表
export function listContract(query) {
  return request({
    url: '/csa/contract/list',
    method: 'get',
    params: query
  })
}

// 查询会员签约详细
export function getContract(contractId) {
  return request({
    url: '/csa/contract/' + contractId,
    method: 'get'
  })
}

// 新增会员签约
export function addContract(data) {
  return request({
    url: '/csa/contract',
    method: 'post',
    data: data
  })
}

// 修改会员签约
export function updateContract(data) {
  return request({
    url: '/csa/contract',
    method: 'put',
    data: data
  })
}

// 删除会员签约
export function delContract(contractId) {
  return request({
    url: '/csa/contract/' + contractId,
    method: 'delete'
  })
}
