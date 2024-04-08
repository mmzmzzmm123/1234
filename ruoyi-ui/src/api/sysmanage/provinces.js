import request from '@/utils/request'

// 查询省份信息列表
export function listProvinces(query) {
  return request({
    url: '/sysmanage/provinces/list',
    method: 'get',
    params: query
  })
}

// 查询省份信息详细
export function getProvinces(id) {
  return request({
    url: '/sysmanage/provinces/' + id,
    method: 'get'
  })
}

// 新增省份信息
export function addProvinces(data) {
  return request({
    url: '/sysmanage/provinces',
    method: 'post',
    data: data
  })
}

// 修改省份信息
export function updateProvinces(data) {
  return request({
    url: '/sysmanage/provinces',
    method: 'put',
    data: data
  })
}

// 删除省份信息
export function delProvinces(id) {
  return request({
    url: '/sysmanage/provinces/' + id,
    method: 'delete'
  })
}
