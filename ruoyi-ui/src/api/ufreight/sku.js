import request from '@/utils/request'

// 查询待处理sku列表
export function listSku(query) {
  return request({
    url: '/ufreight/sku/list',
    method: 'get',
    params: query
  })
}

// 查询待处理sku详细
export function getSku(pendingId) {
  return request({
    url: '/ufreight/sku/' + pendingId,
    method: 'get'
  })
}

// 新增待处理sku
export function addSku(data) {
  return request({
    url: '/ufreight/sku',
    method: 'post',
    data: data
  })
}

// 修改待处理sku
export function updateSku(data) {
  return request({
    url: '/ufreight/sku',
    method: 'put',
    data: data
  })
}

// 删除待处理sku
export function delSku(pendingId) {
  return request({
    url: '/ufreight/sku/' + pendingId,
    method: 'delete'
  })
}
