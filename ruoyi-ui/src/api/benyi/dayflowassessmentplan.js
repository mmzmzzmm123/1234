import request from '@/utils/request'

// 查询幼儿园一日流程评估计划列表
export function listDayflowassessmentplan(query) {
  return request({
    url: '/benyi/dayflowassessmentplan/list',
    method: 'get',
    params: query
  })
}

// 查询幼儿园一日流程评估计划详细
export function getDayflowassessmentplan(id) {
  return request({
    url: '/benyi/dayflowassessmentplan/' + id,
    method: 'get'
  })
}

// 新增幼儿园一日流程评估计划
export function addDayflowassessmentplan(data) {
  return request({
    url: '/benyi/dayflowassessmentplan',
    method: 'post',
    data: data
  })
}

// 修改幼儿园一日流程评估计划
export function updateDayflowassessmentplan(data) {
  return request({
    url: '/benyi/dayflowassessmentplan',
    method: 'put',
    data: data
  })
}

// 删除幼儿园一日流程评估计划
export function delDayflowassessmentplan(id) {
  return request({
    url: '/benyi/dayflowassessmentplan/' + id,
    method: 'delete'
  })
}

// 导出幼儿园一日流程评估计划
export function exportDayflowassessmentplan(query) {
  return request({
    url: '/benyi/dayflowassessmentplan/export',
    method: 'get',
    params: query
  })
}
