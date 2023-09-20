import request from '@/utils/request'

// 查询保洁员信息列表
export function listCleaner(query) {
  return request({
    url: '/office/cleaner/list',
    method: 'get',
    params: query
  })
}

// 查询保洁员信息详细
export function getCleaner(id) {
  return request({
    url: '/office/cleaner/' + id,
    method: 'get'
  })
}

// 新增保洁员信息
export function addCleaner(data) {
  return request({
    url: '/office/cleaner',
    method: 'post',
    data: data
  })
}

// 修改保洁员信息
export function updateCleaner(data) {
  return request({
    url: '/office/cleaner',
    method: 'put',
    data: data
  })
}

// 删除保洁员信息
export function delCleaner(id) {
  return request({
    url: '/office/cleaner/' + id,
    method: 'delete'
  })
}
