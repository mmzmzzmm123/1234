import request from '@/utils/request'

// 查询农场分区列表
export function listZone(query) {
  return request({
    url: '/csa/zone/list',
    method: 'get',
    params: query
  })
}

// 查询农场分区详细
export function getZone(code) {
  return request({
    url: '/csa/zone/' + code,
    method: 'get'
  })
}

// 新增农场分区
export function addZone(data) {
  return request({
    url: '/csa/zone',
    method: 'post',
    data: data
  })
}

// 修改农场分区
export function updateZone(data) {
  return request({
    url: '/csa/zone',
    method: 'put',
    data: data
  })
}

// 删除农场分区
export function delZone(code) {
  return request({
    url: '/csa/zone/' + code,
    method: 'delete'
  })
}
