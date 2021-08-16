import request from '@/utils/request'

// 查询主题整合学期计划列表
export function listTermplan(query) {
  return request({
    url: '/benyi/themetermplan/list',
    method: 'get',
    params: query
  })
}

// 查询主题整合学期计划列表
export function listTermplansp(query) {
  return request({
    url: '/benyi/themetermplan/listsp',
    method: 'get',
    params: query
  })
}

// 查询主题整合学期计划详细
export function getTermplan(id) {
  return request({
    url: '/benyi/themetermplan/' + id,
    method: 'get'
  })
}

// 新增主题整合学期计划
export function addTermplan(data) {
  return request({
    url: '/benyi/themetermplan',
    method: 'post',
    data: data
  })
}

// 修改主题整合学期计划
export function updateTermplan(data) {
  return request({
    url: '/benyi/themetermplan',
    method: 'put',
    data: data
  })
}

// 删除主题整合学期计划
export function delTermplan(id) {
  return request({
    url: '/benyi/themetermplan/' + id,
    method: 'delete'
  })
}

// 提交主题整合学期计划
export function checkTermplan(id) {
  return request({
    url: '/benyi/themetermplan/check/' + id,
    method: 'post'
  })
}

// 导出主题整合学期计划
export function exportTermplan(query) {
  return request({
    url: '/benyi/themetermplan/export',
    method: 'get',
    params: query
  })
}