import request from '@/utils/request'

// 查询题库信息列表
export function listRepo(query) {
  return request({
    url: '/business/repo/list',
    method: 'get',
    params: query
  })
}

// 查询题库信息详细
export function getRepo(id) {
  return request({
    url: '/business/repo/' + id,
    method: 'get'
  })
}

// 新增题库信息
export function addRepo(data) {
  return request({
    url: '/business/repo',
    method: 'post',
    data: data
  })
}

// 修改题库信息
export function updateRepo(data) {
  return request({
    url: '/business/repo',
    method: 'put',
    data: data
  })
}

// 删除题库信息
export function delRepo(id) {
  return request({
    url: '/business/repo/' + id,
    method: 'delete'
  })
}
