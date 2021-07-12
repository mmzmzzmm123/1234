import request from '@/utils/request'

// 查询采集器列表
export function listCollector(query) {
  return request({
    url: '/usv/collector/list',
    method: 'get',
    params: query
  })
}

// 查询采集器详细
export function getCollector(id) {
  return request({
    url: '/usv/collector/' + id,
    method: 'get'
  })
}

// 新增采集器
export function addCollector(data) {
  return request({
    url: '/usv/collector',
    method: 'post',
    data: data
  })
}

// 修改采集器
export function updateCollector(data) {
  return request({
    url: '/usv/collector',
    method: 'put',
    data: data
  })
}

// 删除采集器
export function delCollector(id) {
  return request({
    url: '/usv/collector/' + id,
    method: 'delete'
  })
}

// 导出采集器
export function exportCollector(query) {
  return request({
    url: '/usv/collector/export',
    method: 'get',
    params: query
  })
}