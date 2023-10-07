import request from '@/utils/request'

// 查询心理测评列表
export function listGauge(query) {
  return request({
    url: '/psychology/gauge/list',
    method: 'get',
    params: query
  })
}

export function getGauges(query) {
  return request({
    url: '/psychology/gauge/getGauges',
    method: 'get',
    params: query
  })
}

// 查询心理测评详细
export function getGauge(id) {
  return request({
    url: '/psychology/gauge/' + id,
    method: 'get'
  })
}

// 新增心理测评
export function addGauge(data) {
  return request({
    url: '/psychology/gauge',
    method: 'post',
    data: data
  })
}

// 修改心理测评
export function updateGauge(data) {
  return request({
    url: '/psychology/gauge',
    method: 'put',
    data: data
  })
}

// 删除心理测评
export function delGauge(id) {
  return request({
    url: '/psychology/gauge/' + id,
    method: 'delete'
  })
}
