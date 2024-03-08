import request from '@/utils/request'

// 查询车源&动态关联表列表
export function listRelative(query) {
  return request({
    url: '/api/relative/list',
    method: 'get',
    params: query
  })
}

// 查询车源&动态关联表详细
export function getRelative(id) {
  return request({
    url: '/api/relative/' + id,
    method: 'get'
  })
}

// 新增车源&动态关联表
export function addRelative(data) {
  return request({
    url: '/api/relative',
    method: 'post',
    data: data
  })
}

// 修改车源&动态关联表
export function updateRelative(data) {
  return request({
    url: '/api/relative',
    method: 'put',
    data: data
  })
}

// 删除车源&动态关联表
export function delRelative(id) {
  return request({
    url: '/api/relative/' + id,
    method: 'delete'
  })
}
