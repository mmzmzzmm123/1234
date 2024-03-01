import request from '@/utils/request'

// 查询动态信息管理列表
export function listDynamic(query) {
  return request({
    url: '/api/dynamic/list',
    method: 'get',
    params: query
  })
}

// 查询动态信息管理详细
export function getDynamic(id) {
  return request({
    url: '/api/dynamic/' + id,
    method: 'get'
  })
}

// 新增动态信息管理
export function addDynamic(data) {
  return request({
    url: '/api/dynamic',
    method: 'post',
    data: data
  })
}

// 修改动态信息管理
export function updateDynamic(data) {
  return request({
    url: '/api/dynamic',
    method: 'put',
    data: data
  })
}

// 删除动态信息管理
export function delDynamic(id) {
  return request({
    url: '/api/dynamic/' + id,
    method: 'delete'
  })
}
