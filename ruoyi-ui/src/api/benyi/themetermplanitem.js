import request from '@/utils/request'

// 查询主题整合学期计划明细列表
export function listTermplanitem(query) {
  return request({
    url: '/benyi/themetermplanitem/list',
    method: 'get',
    params: query
  })
}

// 查询主题整合学期计划明细详细
export function getTermplanitem(id) {
  return request({
    url: '/benyi/themetermplanitem/' + id,
    method: 'get'
  })
}

// 新增主题整合学期计划明细
export function addTermplanitem(data) {
  return request({
    url: '/benyi/themetermplanitem',
    method: 'post',
    data: data
  })
}

// 修改主题整合学期计划明细
export function updateTermplanitem(data) {
  return request({
    url: '/benyi/themetermplanitem',
    method: 'put',
    data: data
  })
}

// 删除主题整合学期计划明细
export function delTermplanitem(id) {
  return request({
    url: '/benyi/themetermplanitem/' + id,
    method: 'delete'
  })
}

// 导出主题整合学期计划明细
export function exportTermplanitem(query) {
  return request({
    url: '/benyi/themetermplanitem/export',
    method: 'get',
    params: query
  })
}