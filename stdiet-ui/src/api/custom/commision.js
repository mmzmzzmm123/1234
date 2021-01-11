import request from '@/utils/request'

// 查询业务提成比例列表
export function listCommision(query) {
  return request({
    url: '/custom/commision/list',
    method: 'get',
    params: query
  })
}

// 查询提成明细
export function detailCommision(query) {
  return request({
    url: '/custom/commision/detail',
    method: 'get',
    params: query
  })
}

// 查询业务提成比例详细
export function getCommision(ruleId, postId) {
  return request({
    url: '/custom/commision/' + ruleId,
    method: 'get',
    params: {postId}
  })
}

// 新增业务提成比例
export function addCommision(data) {
  return request({
    url: '/custom/commision',
    method: 'post',
    data: data
  })
}

// 修改业务提成比例
export function updateCommision(data) {
  return request({
    url: '/custom/commision',
    method: 'put',
    data: data
  })
}

// 删除业务提成比例
export function delCommision(ruleId) {
  return request({
    url: '/custom/commision/' + ruleId,
    method: 'delete'
  })
}

// 导出业务提成比例
export function exportCommision(query) {
  return request({
    url: '/custom/commision/export',
    method: 'get',
    params: query
  })
}

export function getSalesman() {
  return request({
    url: '/custom/commision/salesman',
    method: 'get',
  })
}

// 查询按天计算提成明细（营养师、售后）
export function detailDayCommision(query) {
  return request({
    url: '/custom/commision/detailDay',
    method: 'get',
    params: query
  })
}
