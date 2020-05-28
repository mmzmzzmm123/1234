import request from '@/utils/request'

// 查询多级字典管理列表
export function listMoedata(query) {
  return request({
    url: '/system/moedata/list',
    method: 'get',
    params: query
  })
}

// 查询多级字典管理详细
export function getMoedata(id) {
  return request({
    url: '/system/moedata/' + id,
    method: 'get'
  })
}

// 新增多级字典管理
export function addMoedata(data) {
  return request({
    url: '/system/moedata',
    method: 'post',
    data: data
  })
}

// 修改多级字典管理
export function updateMoedata(data) {
  return request({
    url: '/system/moedata',
    method: 'put',
    data: data
  })
}

// 删除多级字典管理
export function delMoedata(id) {
  return request({
    url: '/system/moedata/' + id,
    method: 'delete'
  })
}