import request from '@/utils/request'

// 查询主题整合周计划列表
export function listWeekplan(query) {
  return request({
    url: '/benyi/thememonthplanitem/list',
    method: 'get',
    params: query
  })
}

// 查询主题整合周计划详细
export function getWeekplan(id) {
  return request({
    url: '/benyi/thememonthplanitem/' + id,
    method: 'get'
  })
}

// 新增主题整合周计划
export function addWeekplan(data) {
  return request({
    url: '/benyi/thememonthplanitem',
    method: 'post',
    data: data
  })
}

// 修改主题整合周计划
export function updateWeekplan(data) {
  return request({
    url: '/benyi/thememonthplanitem',
    method: 'put',
    data: data
  })
}

// 删除主题整合周计划
export function delWeekplan(id) {
  return request({
    url: '/benyi/thememonthplanitem/' + id,
    method: 'delete'
  })
}

// 导出主题整合周计划
export function exportWeekplan(query) {
  return request({
    url: '/benyi/thememonthplanitem/export',
    method: 'get',
    params: query
  })
}