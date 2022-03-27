import request from '@/utils/request'

// 查询作物生长阶段列表
export function listPhase(query) {
  return request({
    url: '/csa/phase/list',
    method: 'get',
    params: query
  })
}

// 查询作物生长阶段详细
export function getPhase(id) {
  return request({
    url: '/csa/phase/' + id,
    method: 'get'
  })
}

// 新增作物生长阶段
export function addPhase(data) {
  return request({
    url: '/csa/phase',
    method: 'post',
    data: data
  })
}

// 修改作物生长阶段
export function updatePhase(data) {
  return request({
    url: '/csa/phase',
    method: 'put',
    data: data
  })
}

// 删除作物生长阶段
export function delPhase(id) {
  return request({
    url: '/csa/phase/' + id,
    method: 'delete'
  })
}
