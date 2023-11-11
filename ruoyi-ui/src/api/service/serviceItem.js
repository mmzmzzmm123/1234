import request from '@/utils/request'

// 查询服务子项目列表
export function listServiceItem(query) {
  return request({
    url: '/service/serviceItem/list',
    method: 'get',
    params: query
  })
}

// 查询服务子项目详细
export function getServiceItem(id) {
  return request({
    url: '/service/serviceItem/' + id,
    method: 'get'
  })
}

// 新增服务子项目
export function addServiceItem(data) {
  return request({
    url: '/service/serviceItem',
    method: 'post',
    data: data
  })
}

// 修改服务子项目
export function updateServiceItem(data) {
  return request({
    url: '/service/serviceItem',
    method: 'put',
    data: data
  })
}

// 删除服务子项目
export function delServiceItem(id) {
  return request({
    url: '/service/serviceItem/' + id,
    method: 'delete'
  })
}
