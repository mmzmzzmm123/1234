import request from '@/utils/request'

// 查询主题整合周计划明细列表
export function listWeekplanitem(query) {
  return request({
    url: '/benyi/themeweekplanitem/list',
    method: 'get',
    params: query
  })
}

// 查询主题整合周计划明细详细
export function getWeekplanitem(id) {
  return request({
    url: '/benyi/themeweekplanitem/' + id,
    method: 'get'
  })
}

// 新增主题整合周计划明细
export function addWeekplanitem(data) {
  return request({
    url: '/benyi/themeweekplanitem',
    method: 'post',
    data: data
  })
}

// 修改主题整合周计划明细
export function updateWeekplanitem(data) {
  return request({
    url: '/benyi/themeweekplanitem',
    method: 'put',
    data: data
  })
}

// 删除主题整合周计划明细
export function delWeekplanitem(id) {
  return request({
    url: '/benyi/themeweekplanitem/' + id,
    method: 'delete'
  })
}

// 导出主题整合周计划明细
export function exportWeekplanitem(query) {
  return request({
    url: '/benyi/themeweekplanitem/export',
    method: 'get',
    params: query
  })
}