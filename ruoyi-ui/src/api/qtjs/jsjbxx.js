import request from '@/utils/request'

// 查询教师基本信息列表
export function listJsjbxx(query) {
  return request({
    url: '/qtjs/jsjbxx/list',
    method: 'get',
    params: query
  })
}

// 查询教师基本信息详细
export function getJsjbxx(id) {
  return request({
    url: '/qtjs/jsjbxx/' + id,
    method: 'get'
  })
}

// 新增教师基本信息
export function addJsjbxx(data) {
  return request({
    url: '/qtjs/jsjbxx',
    method: 'post',
    data: data
  })
}

// 修改教师基本信息
export function updateJsjbxx(data) {
  return request({
    url: '/qtjs/jsjbxx',
    method: 'put',
    data: data
  })
}

// 删除教师基本信息
export function delJsjbxx(id) {
  return request({
    url: '/qtjs/jsjbxx/' + id,
    method: 'delete'
  })
}

// 导出教师基本信息
export function exportJsjbxx(query) {
  return request({
    url: '/qtjs/jsjbxx/export',
    method: 'get',
    params: query
  })
}