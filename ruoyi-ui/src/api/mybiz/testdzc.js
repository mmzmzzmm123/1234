import request from '@/utils/request'

// 查询测试使用列表
export function listTestdzc(query) {
  return request({
    url: '/mybiz/testdzc/list',
    method: 'get',
    params: query
  })
}

// 查询测试使用详细
export function getTestdzc(id) {
  return request({
    url: '/mybiz/testdzc/' + id,
    method: 'get'
  })
}

// 新增测试使用
export function addTestdzc(data) {
  return request({
    url: '/mybiz/testdzc',
    method: 'post',
    data: data
  })
}

// 修改测试使用
export function updateTestdzc(data) {
  return request({
    url: '/mybiz/testdzc',
    method: 'put',
    data: data
  })
}

// 删除测试使用
export function delTestdzc(id) {
  return request({
    url: '/mybiz/testdzc/' + id,
    method: 'delete'
  })
}
