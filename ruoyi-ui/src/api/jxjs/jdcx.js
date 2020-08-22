import request from '@/utils/request'

// 查询基地区级审核列表
export function listJdcx(query) {
  return request({
    url: '/jxjs/jdcx/list',
    method: 'get',
    params: query
  })
}

// 查询基地区级审核详细
export function getJdcx(id) {
  return request({
    url: '/jxjs/jdcx/' + id,
    method: 'get'
  })
}

// 新增基地区级审核
export function addJdcx(data) {
  return request({
    url: '/jxjs/jdcx',
    method: 'post',
    data: data
  })
}

// 修改基地区级审核
export function updateJdcx(data) {
  return request({
    url: '/jxjs/jdcx',
    method: 'put',
    data: data
  })
}

// 删除基地区级审核
export function delJdcx(id) {
  return request({
    url: '/jxjs/jdcx/' + id,
    method: 'delete'
  })
}

// 导出基地区级审核
export function exportJdcx(query) {
  return request({
    url: '/jxjs/jdcx/export',
    method: 'get',
    params: query
  })
}

// 删除基地区级审核
export function checkJdcx(id) {
  return request({
    url: '/jxjs/jdcx/check/' + id,
    method: 'post'
  })
}