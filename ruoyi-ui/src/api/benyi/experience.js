import request from '@/utils/request'

// 查询入班体验申请列表
export function listExperience(query) {
  return request({
    url: '/benyi/experience/list',
    method: 'get',
    params: query
  })
}

// 查询入班体验申请详细
export function getExperience(id) {
  return request({
    url: '/benyi/experience/' + id,
    method: 'get'
  })
}

// 查询入班体验申请详细
export function getExperience_query(query) {
  return request({
    url: '/benyi/experience/getInfo',
    method: 'get',
    params: query
  })
}

// 新增入班体验申请
export function addExperience(data) {
  return request({
    url: '/benyi/experience/add',
    method: 'post',
    data: data
  })
}

// 修改入班体验申请
export function updateExperience(data) {
  return request({
    url: '/benyi/experience',
    method: 'put',
    data: data
  })
}

// 删除入班体验申请
export function delExperience(id) {
  return request({
    url: '/benyi/experience/' + id,
    method: 'delete'
  })
}

// 导出入班体验申请
export function exportExperience(query) {
  return request({
    url: '/benyi/experience/export',
    method: 'get',
    params: query
  })
}
