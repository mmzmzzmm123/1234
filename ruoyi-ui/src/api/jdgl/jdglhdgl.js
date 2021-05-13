import request from '@/utils/request'

// 查询基地管理活动管理列表
export function listJdglhdgl(query) {
  return request({
    url: '/jdgl/jdglhdgl/list',
    method: 'get',
    params: query
  })
}

// 查询基地管理活动管理详细
export function getJdglhdgl(id) {
  return request({
    url: '/jdgl/jdglhdgl/' + id,
    method: 'get'
  })
}

// 新增基地管理活动管理
export function addJdglhdgl(data) {
  return request({
    url: '/jdgl/jdglhdgl',
    method: 'post',
    data: data
  })
}

// 修改基地管理活动管理
export function updateJdglhdgl(data) {
  return request({
    url: '/jdgl/jdglhdgl',
    method: 'put',
    data: data
  })
}

// 删除基地管理活动管理
export function delJdglhdgl(id) {
  return request({
    url: '/jdgl/jdglhdgl/' + id,
    method: 'delete'
  })
}

// 导出基地管理活动管理
export function exportJdglhdgl(query) {
  return request({
    url: '/jdgl/jdglhdgl/export',
    method: 'get',
    params: query
  })
}