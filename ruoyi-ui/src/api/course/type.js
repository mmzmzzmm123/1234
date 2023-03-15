import request from '@/utils/request'

// 查询课程类型列表
export function listType(query) {
  return request({
    url: '/course/type/list',
    method: 'get',
    params: query
  })
}

// 查询课程类型详细
export function getType(typeId) {
  return request({
    url: '/course/type/' + typeId,
    method: 'get'
  })
}

// 新增课程类型
export function addType(data) {
  return request({
    url: '/course/type',
    method: 'post',
    data: data
  })
}

// 修改课程类型
export function updateType(data) {
  return request({
    url: '/course/type',
    method: 'put',
    data: data
  })
}

// 删除课程类型
export function delType(typeId) {
  return request({
    url: '/system/type/' + typeId,
    method: 'delete'
  })
}
