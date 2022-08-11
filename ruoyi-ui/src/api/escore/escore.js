import request from '@/utils/request'

// 查询分数列表
export function listEscore(query) {
  return request({
    url: '/escore/escore/list',
    method: 'get',
    params: query
  })
}

// 查询分数详细
export function getEscore(id) {
  return request({
    url: '/escore/escore/' + id,
    method: 'get'
  })
}

// 新增分数
export function addEscore(data) {
  return request({
    url: '/escore/escore',
    method: 'post',
    data: data
  })
}

// 修改分数
export function updateEscore(data) {
  return request({
    url: '/escore/escore',
    method: 'put',
    data: data
  })
}

// 删除分数
export function delEscore(id) {
  return request({
    url: '/escore/escore/' + id,
    method: 'delete'
  })
}
