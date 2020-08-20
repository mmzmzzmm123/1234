import request from '@/utils/request'

// 查询基地校列表
export function listJdx(query) {
  return request({
    url: '/jxjs/jdx/list',
    method: 'get',
    params: query
  })
}

// 查询基地校详细
export function getJdx(id) {
  return request({
    url: '/jxjs/jdx/' + id,
    method: 'get'
  })
}

// 新增基地校
export function addJdx(data) {
  return request({
    url: '/jxjs/jdx',
    method: 'post',
    data: data
  })
}

// 修改基地校
export function updateJdx(data) {
  return request({
    url: '/jxjs/jdx',
    method: 'put',
    data: data
  })
}

// 删除基地校
export function delJdx(id) {
  return request({
    url: '/jxjs/jdx/' + id,
    method: 'delete'
  })
}

// 导出基地校
export function exportJdx(query) {
  return request({
    url: '/jxjs/jdx/export',
    method: 'get',
    params: query
  })
}