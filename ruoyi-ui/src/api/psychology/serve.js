import request from '@/utils/request'

// 查询咨询类型列表
export function listConsultServe(query) {
  return request({
    url: '/psychology/serve/list',
    method: 'get',
    params: query
  })
}

// 查询咨询类型列表
export function getServeAll(query) {
  return request({
    url: '/psychology/serve/getAll',
    method: 'get',
    params: query
  })
}

// 查询咨询类型详细
export function getConsultServe(id) {
  return request({
    url: '/psychology/serve/' + id,
    method: 'get'
  })
}

// 新增咨询类型
export function addConsultServe(data) {
  return request({
    url: '/psychology/serve',
    method: 'post',
    data: data
  })
}

// 修改咨询类型
export function updateConsultServe(data) {
  return request({
    url: '/psychology/serve',
    method: 'put',
    data: data
  })
}

// 删除咨询类型
export function delConsultServe(id) {
  return request({
    url: '/psychology/serve/' + id,
    method: 'delete'
  })
}
