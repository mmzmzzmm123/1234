import request from '@/utils/request'

// 查询课程banner配置列表
export function listConfig(query) {
  return request({
    url: '/course/config/list',
    method: 'get',
    params: query
  })
}

// 查询课程banner配置详细
export function getConfig(id) {
  return request({
    url: '/course/config/' + id,
    method: 'get'
  })
}

// 新增课程banner配置
export function addConfig(data) {
  return request({
    url: '/course/config',
    method: 'post',
    data: data
  })
}

// 修改课程banner配置
export function updateConfig(data) {
  return request({
    url: '/course/config',
    method: 'put',
    data: data
  })
}

// 删除课程banner配置
export function delConfig(id) {
  return request({
    url: '/course/config/' + id,
    method: 'delete'
  })
}
