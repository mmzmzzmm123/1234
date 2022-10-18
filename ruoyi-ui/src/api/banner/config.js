import request from '@/utils/request'

// 查询测评banner配置列表
export function listConfig(query) {
  return request({
    url: '/banner/config/list',
    method: 'get',
    params: query
  })
}

// 查询测评banner配置详细
export function getConfig(id) {
  return request({
    url: '/banner/config/' + id,
    method: 'get'
  })
}

// 新增测评banner配置
export function addConfig(data) {
  return request({
    url: '/banner/config',
    method: 'post',
    data: data
  })
}

// 修改测评banner配置
export function updateConfig(data) {
  return request({
    url: '/banner/config',
    method: 'put',
    data: data
  })
}

// 删除测评banner配置
export function delConfig(id) {
  return request({
    url: '/banner/config/' + id,
    method: 'delete'
  })
}
