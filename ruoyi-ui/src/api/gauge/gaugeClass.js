import request from '@/utils/request'

// 查询测评分类列表
export function listGaugeClass(query) {
  return request({
    url: '/psychology/gaugeClass/list',
    method: 'get',
    params: query
  })
}

// 查询测评分类详细
export function getGaugeClass(id) {
  return request({
    url: '/psychology/gaugeClass/' + id,
    method: 'get'
  })
}

// 新增测评分类
export function addGaugeClass(data) {
  return request({
    url: '/psychology/gaugeClass',
    method: 'post',
    data: data
  })
}

// 修改测评分类
export function updateGaugeClass(data) {
  return request({
    url: '/psychology/gaugeClass',
    method: 'put',
    data: data
  })
}

// 删除测评分类
export function delGaugeClass(id) {
  return request({
    url: '/psychology/gaugeClass/' + id,
    method: 'delete'
  })
}
