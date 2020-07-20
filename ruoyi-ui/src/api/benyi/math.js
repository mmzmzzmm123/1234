import request from '@/utils/request'

// 查询游戏数学列表
export function listMath(query) {
  return request({
    url: '/benyi/math/list',
    method: 'get',
    params: query
  })
}

// 查询游戏数学详细
export function getMath(id) {
  return request({
    url: '/benyi/math/' + id,
    method: 'get'
  })
}

// 新增游戏数学
export function addMath(data) {
  return request({
    url: '/benyi/math',
    method: 'post',
    data: data
  })
}

// 修改游戏数学
export function updateMath(data) {
  return request({
    url: '/benyi/math',
    method: 'put',
    data: data
  })
}

// 删除游戏数学
export function delMath(id) {
  return request({
    url: '/benyi/math/' + id,
    method: 'delete'
  })
}

// 导出游戏数学
export function exportMath(query) {
  return request({
    url: '/benyi/math/export',
    method: 'get',
    params: query
  })
}