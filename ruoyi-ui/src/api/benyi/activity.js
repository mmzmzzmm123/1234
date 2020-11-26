import request from '@/utils/request'

// 查询主题整合活动列表
export function listActivity(query) {
  return request({
    url: '/benyi/activity/list',
    method: 'get',
    params: query
  })
}

// 查询主题整合活动详细
export function getActivity(id) {
  return request({
    url: '/benyi/activity/' + id,
    method: 'get'
  })
}

// 新增主题整合活动
export function addActivity(data) {
  return request({
    url: '/benyi/activity',
    method: 'post',
    data: data
  })
}

// 修改主题整合活动
export function updateActivity(data) {
  return request({
    url: '/benyi/activity',
    method: 'put',
    data: data
  })
}

// 删除主题整合活动
export function delActivity(id) {
  return request({
    url: '/benyi/activity/' + id,
    method: 'delete'
  })
}

// 导出主题整合活动
export function exportActivity(query) {
  return request({
    url: '/benyi/activity/export',
    method: 'get',
    params: query
  })
}

// 查询主题整合活动
export function listActivityByThemeId(id) {
  return request({
    url: '/benyi/activity/listbythemeid/' + id,
    method: 'post'
  })
}

// 查询主题整合活动
export function listActivityById(id) {
  return request({
    url: '/benyi/activity/listbyid/' + id,
    method: 'post'
  })
}