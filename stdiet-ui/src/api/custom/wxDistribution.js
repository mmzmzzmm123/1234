import request from '@/utils/request'

// 查询微信分配管理列表
export function listWxDistribution(query) {
  return request({
    url: '/custom/wxDistribution/list',
    method: 'get',
    params: query
  })
}

// 查询微信分配管理详细
export function getWxDistribution(id) {
  return request({
    url: '/custom/wxDistribution/' + id,
    method: 'get'
  })
}

// 新增微信分配管理
export function addWxDistribution(data) {
  return request({
    url: '/custom/wxDistribution',
    method: 'post',
    data: data
  })
}

// 修改微信分配管理
export function updateWxDistribution(data) {
  return request({
    url: '/custom/wxDistribution',
    method: 'put',
    data: data
  })
}

// 删除微信分配管理
export function delWxDistribution(id) {
  return request({
    url: '/custom/wxDistribution/' + id,
    method: 'delete'
  })
}

// 导出微信分配管理
export function exportWxDistribution(query) {
  return request({
    url: '/custom/wxDistribution/export',
    method: 'get',
    params: query
  })
}
