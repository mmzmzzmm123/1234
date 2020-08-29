import request from '@/utils/request'

// 查询考核过程数据列表
export function listJxzxkhgcsj(query) {
  return request({
    url: '/jxzxkhgl/jxzxkhgcsj/list',
    method: 'get',
    params: query
  })
}

// 查询考核过程数据详细
export function getJxzxkhgcsj(id) {
  return request({
    url: '/jxzxkhgl/jxzxkhgcsj/' + id,
    method: 'get'
  })
}

// 新增考核过程数据
export function addJxzxkhgcsj(data) {
  return request({
    url: '/jxzxkhgl/jxzxkhgcsj',
    method: 'post',
    data: data
  })
}

// 修改考核过程数据
export function updateJxzxkhgcsj(data) {
  return request({
    url: '/jxzxkhgl/jxzxkhgcsj',
    method: 'put',
    data: data
  })
}

// 删除考核过程数据
export function delJxzxkhgcsj(id) {
  return request({
    url: '/jxzxkhgl/jxzxkhgcsj/' + id,
    method: 'delete'
  })
}

// 导出考核过程数据
export function exportJxzxkhgcsj(query) {
  return request({
    url: '/jxzxkhgl/jxzxkhgcsj/export',
    method: 'get',
    params: query
  })
}