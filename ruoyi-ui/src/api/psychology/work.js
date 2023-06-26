import request from '@/utils/request'

// 查询咨询服务列表
export function listWork(query) {
  return request({
    url: '/psychology/work/list',
    method: 'get',
    params: query
  })
}

// 查询咨询服务详细
export function getWork(id) {
  return request({
    url: '/psychology/work/' + id,
    method: 'get'
  })
}

// 新增咨询服务
export function addWork(data) {
  return request({
    url: '/psychology/work',
    method: 'post',
    data: data
  })
}

// 修改咨询服务
export function updateWork(data) {
  return request({
    url: '/psychology/work',
    method: 'put',
    data: data
  })
}

// 删除咨询服务
export function delWork(id) {
  return request({
    url: '/psychology/work/' + id,
    method: 'delete'
  })
}
