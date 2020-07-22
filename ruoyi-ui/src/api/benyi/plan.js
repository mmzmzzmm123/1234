import request from '@/utils/request'

// 查询游戏数学方案列表
export function listPlan(query) {
  return request({
    url: '/benyi/plan/list',
    method: 'get',
    params: query
  })
}

// 查询游戏数学方案详细
export function getPlan(id) {
  return request({
    url: '/benyi/plan/' + id,
    method: 'get'
  })
}

// 新增游戏数学方案
export function addPlan(data) {
  return request({
    url: '/benyi/plan',
    method: 'post',
    data: data
  })
}

// 修改游戏数学方案
export function updatePlan(data) {
  return request({
    url: '/benyi/plan',
    method: 'put',
    data: data
  })
}

// 删除游戏数学方案
export function delPlan(id) {
  return request({
    url: '/benyi/plan/' + id,
    method: 'delete'
  })
}

// 导出游戏数学方案
export function exportPlan(query) {
  return request({
    url: '/benyi/plan/export',
    method: 'get',
    params: query
  })
}