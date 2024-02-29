import request from '@/utils/request'

// 查询动态管理列表
export function listDynamic(query) {
  return request({
    url: '/system/dynamic/list',
    method: 'get',
    params: query
  })
}

// 查询动态管理详细
export function getDynamic(id) {
  return request({
    url: '/system/dynamic/' + id,
    method: 'get'
  })
}

// 新增动态管理
export function addDynamic(data) {
  return request({
    url: '/system/dynamic',
    method: 'post',
    data: data
  })
}

// 修改动态管理
export function updateDynamic(data) {
  return request({
    url: '/system/dynamic',
    method: 'put',
    data: data
  })
}

// 删除动态管理
export function delDynamic(id) {
  return request({
    url: '/system/dynamic/' + id,
    method: 'delete'
  })
}
