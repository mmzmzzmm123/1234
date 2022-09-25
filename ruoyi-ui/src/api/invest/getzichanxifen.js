import request from '@/utils/request'

// 查询资产细分列表
export function listGetzichanxifen(query) {
  return request({
    url: '/invest/getzichanxifen/list',
    method: 'get',
    params: query
  })
}

// 查询资产细分详细
export function getGetzichanxifen(name) {
  return request({
    url: '/invest/getzichanxifen/' + name,
    method: 'get'
  })
}

// 新增资产细分
export function addGetzichanxifen(data) {
  return request({
    url: '/invest/getzichanxifen',
    method: 'post',
    data: data
  })
}

// 修改资产细分
export function updateGetzichanxifen(data) {
  return request({
    url: '/invest/getzichanxifen',
    method: 'put',
    data: data
  })
}

// 删除资产细分
export function delGetzichanxifen(name) {
  return request({
    url: '/invest/getzichanxifen/' + name,
    method: 'delete'
  })
}
