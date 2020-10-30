import request from '@/utils/request'

// 查询游戏数学学期计划明细列表
export function listMathtermplanitem(query) {
  return request({
    url: '/benyi/mathtermplanitem/list',
    method: 'get',
    params: query
  })
}

// 查询游戏数学学期计划明细详细
export function getMathtermplanitem(id) {
  return request({
    url: '/benyi/mathtermplanitem/' + id,
    method: 'get'
  })
}

// 新增游戏数学学期计划明细
export function addMathtermplanitem(data) {
  return request({
    url: '/benyi/mathtermplanitem',
    method: 'post',
    data: data
  })
}

// 修改游戏数学学期计划明细
export function updateMathtermplanitem(data) {
  return request({
    url: '/benyi/mathtermplanitem',
    method: 'put',
    data: data
  })
}

// 删除游戏数学学期计划明细
export function delMathtermplanitem(id) {
  return request({
    url: '/benyi/mathtermplanitem/' + id,
    method: 'delete'
  })
}

// 导出游戏数学学期计划明细
export function exportMathtermplanitem(query) {
  return request({
    url: '/benyi/mathtermplanitem/export',
    method: 'get',
    params: query
  })
}