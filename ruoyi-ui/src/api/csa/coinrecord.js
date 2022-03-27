import request from '@/utils/request'

// 查询金币记录列表
export function listCoinrecord(query) {
  return request({
    url: '/csa/coinrecord/list',
    method: 'get',
    params: query
  })
}

// 查询金币记录详细
export function getCoinrecord(id) {
  return request({
    url: '/csa/coinrecord/' + id,
    method: 'get'
  })
}

// 新增金币记录
export function addCoinrecord(data) {
  return request({
    url: '/csa/coinrecord',
    method: 'post',
    data: data
  })
}

// 修改金币记录
export function updateCoinrecord(data) {
  return request({
    url: '/csa/coinrecord',
    method: 'put',
    data: data
  })
}

// 删除金币记录
export function delCoinrecord(id) {
  return request({
    url: '/csa/coinrecord/' + id,
    method: 'delete'
  })
}
