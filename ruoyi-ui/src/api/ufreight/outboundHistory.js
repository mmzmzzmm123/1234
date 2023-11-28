import request from '@/utils/request'

// 查询出库流水列表
export function listOutboundHistory(query) {
  return request({
    url: '/ufreight/outboundHistory/list',
    method: 'get',
    params: query
  })
}

// 查询出库流水详细
export function getOutboundHistory(outboundId) {
  return request({
    url: '/ufreight/outboundHistory/' + outboundId,
    method: 'get'
  })
}

// 新增出库流水
export function addOutboundHistory(data) {
  return request({
    url: '/ufreight/outboundHistory',
    method: 'post',
    data: data
  })
}

// 修改出库流水
export function updateOutboundHistory(data) {
  return request({
    url: '/ufreight/outboundHistory',
    method: 'put',
    data: data
  })
}

// 删除出库流水
export function delOutboundHistory(outboundId) {
  return request({
    url: '/ufreight/outboundHistory/' + outboundId,
    method: 'delete'
  })
}
