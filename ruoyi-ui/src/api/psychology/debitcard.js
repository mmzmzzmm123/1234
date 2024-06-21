import request from '@/utils/request'

// 查询客户银行卡列表
export function listDebitcard(query) {
  return request({
    url: '/system/debitcard/list',
    method: 'get',
    params: query
  })
}

// 查询客户银行卡详细
export function getDebitcard(cardNumber) {
  return request({
    url: '/system/debitcard/' + cardNumber,
    method: 'get'
  })
}

// 新增客户银行卡
export function addDebitcard(data) {
  return request({
    url: '/system/debitcard',
    method: 'post',
    data: data
  })
}

// 修改客户银行卡
export function updateDebitcard(data) {
  return request({
    url: '/system/debitcard',
    method: 'put',
    data: data
  })
}

// 删除客户银行卡
export function delDebitcard(cardNumber) {
  return request({
    url: '/system/debitcard/' + cardNumber,
    method: 'delete'
  })
}
