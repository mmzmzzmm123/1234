import request from '@/utils/request'

// 查询分销关系列表
export function listDistributionRelation(query) {
  return request({
    url: '/distribution/distributionRelation/list',
    method: 'get',
    params: query
  })
}

// 查询分销关系详细
export function getDistributionRelation(id) {
  return request({
    url: '/distribution/distributionRelation/' + id,
    method: 'get'
  })
}

// 新增分销关系
export function addDistributionRelation(data) {
  return request({
    url: '/distribution/distributionRelation',
    method: 'post',
    data: data
  })
}

// 修改分销关系
export function updateDistributionRelation(data) {
  return request({
    url: '/distribution/distributionRelation',
    method: 'put',
    data: data
  })
}

// 删除分销关系
export function delDistributionRelation(id) {
  return request({
    url: '/distribution/distributionRelation/' + id,
    method: 'delete'
  })
}
