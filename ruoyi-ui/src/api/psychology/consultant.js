import request from '@/utils/request'

// 查询心理咨询师列表
export function listConsultant(query) {
  return request({
    url: '/psychology/consultant/list',
    method: 'get',
    params: query
  })
}

// 查询心理咨询师详细
export function getConsultant(id) {
  return request({
    url: '/psychology/consultant/' + id,
    method: 'get'
  })
}

// 新增心理咨询师
export function addConsultant(data) {
  return request({
    url: '/psychology/consultant',
    method: 'post',
    data: data
  })
}

// 修改心理咨询师
export function updateConsultant(data) {
  return request({
    url: '/psychology/consultant',
    method: 'put',
    data: data
  })
}

// 删除心理咨询师
export function delConsultant(id) {
  return request({
    url: '/psychology/consultant/' + id,
    method: 'delete'
  })
}
