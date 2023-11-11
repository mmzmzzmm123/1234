import request from '@/utils/request'

// 查询服务信息列表
export function listServiceInfo(query) {
  return request({
    url: '/service/serviceInfo/list',
    method: 'get',
    params: query
  })
}

// 查询服务信息详细
export function getServiceInfo(id) {
  return request({
    url: '/service/serviceInfo/' + id,
    method: 'get'
  })
}

// 新增服务信息
export function addServiceInfo(data) {
  return request({
    url: '/service/serviceInfo',
    method: 'post',
    data: data
  })
}

// 修改服务信息
export function updateServiceInfo(data) {
  return request({
    url: '/service/serviceInfo',
    method: 'put',
    data: data
  })
}

// 删除服务信息
export function delServiceInfo(id) {
  return request({
    url: '/service/serviceInfo/' + id,
    method: 'delete'
  })
}
