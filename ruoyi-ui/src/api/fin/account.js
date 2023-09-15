import request from '@/utils/request'

// 查询记账账户列表
export function listAccount(query) {
  return request({
    url: '/fin/account/list',
    method: 'get',
    params: query
  })
}

// 查询记账账户详细
export function getAccount(acctId) {
  return request({
    url: '/fin/account/' + acctId,
    method: 'get'
  })
}

// 新增记账账户
export function addAccount(data) {
  return request({
    url: '/fin/account',
    method: 'post',
    data: data
  })
}

// 修改记账账户
export function updateAccount(data) {
  return request({
    url: '/fin/account',
    method: 'put',
    data: data
  })
}

// 删除记账账户
export function delAccount(acctId) {
  return request({
    url: '/fin/account/' + acctId,
    method: 'delete'
  })
}
