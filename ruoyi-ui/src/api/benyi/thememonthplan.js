import request from '@/utils/request'

// 查询主题整合月计划列表
export function listMonthplan(query) {
  return request({
    url: '/benyi/thememonthplan/list',
    method: 'get',
    params: query
  })
}

// 查询主题整合月计划详细
export function getMonthplan(id) {
  return request({
    url: '/benyi/thememonthplan/' + id,
    method: 'get'
  })
}

// 新增主题整合月计划
export function addMonthplan(data) {
  return request({
    url: '/benyi/thememonthplan',
    method: 'post',
    data: data
  })
}

// 修改主题整合月计划
export function updateMonthplan(data) {
  return request({
    url: '/benyi/thememonthplan',
    method: 'put',
    data: data
  })
}

// 删除主题整合月计划
export function delMonthplan(id) {
  return request({
    url: '/benyi/thememonthplan/' + id,
    method: 'delete'
  })
}

// 导出主题整合月计划
export function exportMonthplan(query) {
  return request({
    url: '/benyi/thememonthplan/export',
    method: 'get',
    params: query
  })
}
