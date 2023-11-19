import request from '@/utils/request'

// 查询入库流水列表
export function listInboundHistory(query) {
  return request({
    url: '/ufreight/inboundHistory/list',
    method: 'get',
    params: query
  })
}

// 查询入库流水详细
export function getInboundHistory(inboundId) {
  return request({
    url: '/ufreight/inboundHistory/' + inboundId,
    method: 'get'
  })
}

// 新增入库流水
export function addInboundHistory(data) {
  return request({
    url: '/ufreight/inboundHistory',
    method: 'post',
    data: data
  })
}

// 修改入库流水
export function updateInboundHistory(data) {
  return request({
    url: '/ufreight/inboundHistory',
    method: 'put',
    data: data
  })
}

// 删除入库流水
export function delInboundHistory(inboundId) {
  return request({
    url: '/ufreight/inboundHistory/' + inboundId,
    method: 'delete'
  })
}
