import request from '@/utils/request'

// 查询主题整合周计划列表
export function listMonthplanitem(query) {
  return request({
    url: '/benyi/thememonthplanitem/list',
    method: 'get',
    params: query
  })
}

// 查询主题整合周计划详细
export function getMonthplanitem(id) {
  return request({
    url: '/benyi/thememonthplanitem/' + id,
    method: 'get'
  })
}

// 新增主题整合周计划
export function addMonthplanitem(data) {
  return request({
    url: '/benyi/thememonthplanitem',
    method: 'post',
    data: data
  })
}

// 修改主题整合周计划
export function updateMonthplanitem(data) {
  return request({
    url: '/benyi/thememonthplanitem',
    method: 'put',
    data: data
  })
}

// 删除主题整合周计划
export function delMonthplanitem(id) {
  return request({
    url: '/benyi/thememonthplanitem/' + id,
    method: 'delete'
  })
}

// 导出主题整合周计划
export function exportMonthplanitem(query) {
  return request({
    url: '/benyi/thememonthplanitem/export',
    method: 'get',
    params: query
  })
}