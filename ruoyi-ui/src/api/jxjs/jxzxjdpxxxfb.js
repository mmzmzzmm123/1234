import request from '@/utils/request'

// 查询基地培训信息发布列表
export function listJxzxjdpxxxfb(query) {
  return request({
    url: '/jxjs/jxzxjdpxxxfb/list',
    method: 'get',
    params: query
  })
}

// 查询基地培训信息发布详细
export function getJxzxjdpxxxfb(id) {
  return request({
    url: '/jxjs/jxzxjdpxxxfb/' + id,
    method: 'get'
  })
}

// 新增基地培训信息发布
export function addJxzxjdpxxxfb(data) {
  return request({
    url: '/jxjs/jxzxjdpxxxfb',
    method: 'post',
    data: data
  })
}

// 修改基地培训信息发布
export function updateJxzxjdpxxxfb(data) {
  return request({
    url: '/jxjs/jxzxjdpxxxfb',
    method: 'put',
    data: data
  })
}

// 删除基地培训信息发布
export function delJxzxjdpxxxfb(id) {
  return request({
    url: '/jxjs/jxzxjdpxxxfb/' + id,
    method: 'delete'
  })
}

// 导出基地培训信息发布
export function exportJxzxjdpxxxfb(query) {
  return request({
    url: '/jxjs/jxzxjdpxxxfb/export',
    method: 'get',
    params: query
  })
}