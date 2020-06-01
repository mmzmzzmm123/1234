import request from '@/utils/request'

// 查询培训视频评分列表
export function listScore(query) {
  return request({
    url: '/benyi/score/list',
    method: 'get',
    params: query
  })
}

// 查询培训视频评分详细
export function getScore(id) {
  return request({
    url: '/benyi/score/' + id,
    method: 'get'
  })
}

// 新增培训视频评分
export function addScore(data) {
  return request({
    url: '/benyi/score',
    method: 'post',
    data: data
  })
}

// 修改培训视频评分
export function updateScore(data) {
  return request({
    url: '/benyi/score',
    method: 'put',
    data: data
  })
}

// 删除培训视频评分
export function delScore(id) {
  return request({
    url: '/benyi/score/' + id,
    method: 'delete'
  })
}

// 导出培训视频评分
export function exportScore(query) {
  return request({
    url: '/benyi/score/export',
    method: 'get',
    params: query
  })
}