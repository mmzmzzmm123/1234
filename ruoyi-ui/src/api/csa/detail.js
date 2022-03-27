import request from '@/utils/request'

// 查询采摘任务明细列表
export function listDetail(query) {
  return request({
    url: '/csa/detail/list',
    method: 'get',
    params: query
  })
}

// 查询采摘任务明细详细
export function getDetail(id) {
  return request({
    url: '/csa/detail/' + id,
    method: 'get'
  })
}

// 新增采摘任务明细
export function addDetail(data) {
  return request({
    url: '/csa/detail',
    method: 'post',
    data: data
  })
}

// 修改采摘任务明细
export function updateDetail(data) {
  return request({
    url: '/csa/detail',
    method: 'put',
    data: data
  })
}

// 删除采摘任务明细
export function delDetail(id) {
  return request({
    url: '/csa/detail/' + id,
    method: 'delete'
  })
}
