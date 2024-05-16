import request from '@/utils/request'

// 查询题库管理列表
export function listRepo(query) {
  return request({
    url: '/exammanage/repo/list',
    method: 'get',
    params: query
  })
}

// 查询题库管理详细
export function getRepo(id) {
  return request({
    url: '/exammanage/repo/' + id,
    method: 'get'
  })
}

// 新增题库管理
export function addRepo(data) {
  return request({
    url: '/exammanage/repo',
    method: 'post',
    data: data
  })
}

// 修改题库管理
export function updateRepo(data) {
  return request({
    url: '/exammanage/repo',
    method: 'put',
    data: data
  })
}

// 删除题库管理
export function delRepo(id) {
  return request({
    url: '/exammanage/repo/' + id,
    method: 'delete'
  })
}
