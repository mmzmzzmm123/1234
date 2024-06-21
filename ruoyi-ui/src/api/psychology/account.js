import request from '@/utils/request'

// 查询账户列表
export function listAccount(query) {
  return request({
    url: '/finance/account/list',
    method: 'get',
    params: query
  })
}

// 查询账户详细
export function getAccount(consultantId) {
  return request({
    url: '/finance/account/' + consultantId,
    method: 'get'
  })
}

// 新增账户
export function addAccount(data) {
  return request({
    url: '/finance/account',
    method: 'post',
    data: data
  })
}

// 修改账户
export function updateAccount(data) {
  return request({
    url: '/finance/account',
    method: 'put',
    data: data
  })
}

// 删除账户
export function delAccount(consultantId) {
  return request({
    url: '/finance/account/' + consultantId,
    method: 'delete'
  })
}
