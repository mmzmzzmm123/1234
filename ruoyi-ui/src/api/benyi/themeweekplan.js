import request from '@/utils/request'

// 查询主题整合周计划（根据月计划明细）列表
export function listWeekplan(query) {
  return request({
    url: '/benyi/themeweekplan/list',
    method: 'get',
    params: query
  })
}

// 查询主题整合周计划（根据月计划明细）列表
export function listWeekplansp(query) {
  return request({
    url: '/benyi/themeweekplan/listsp',
    method: 'get',
    params: query
  })
}

// 查询主题整合周计划（根据月计划明细）详细
export function getWeekplan(id) {
  return request({
    url: '/benyi/themeweekplan/' + id,
    method: 'get'
  })
}

// 新增主题整合周计划（根据月计划明细）
export function addWeekplan(data) {
  return request({
    url: '/benyi/themeweekplan',
    method: 'post',
    data: data
  })
}

// 修改主题整合周计划（根据月计划明细）
export function updateWeekplan(data) {
  return request({
    url: '/benyi/themeweekplan',
    method: 'put',
    data: data
  })
}

// 删除主题整合周计划（根据月计划明细）
export function delWeekplan(id) {
  return request({
    url: '/benyi/themeweekplan/' + id,
    method: 'delete'
  })
}

// 提交主题整合学期计划
export function checkWeekplan(id) {
  return request({
    url: '/benyi/themeweekplan/check/' + id,
    method: 'post'
  })
}

// 导出主题整合周计划（根据月计划明细）
export function exportWeekplan(query) {
  return request({
    url: '/benyi/themeweekplan/export',
    method: 'get',
    params: query
  })
}