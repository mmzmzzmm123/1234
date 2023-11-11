import request from '@/utils/request'

// 查询分销佣金比例列表
export function listDistributionCommissionRate(query) {
  return request({
    url: '/distribution/distributionCommissionRate/list',
    method: 'get',
    params: query
  })
}

// 查询分销佣金比例详细
export function getDistributionCommissionRate(id) {
  return request({
    url: '/distribution/distributionCommissionRate/' + id,
    method: 'get'
  })
}

// 新增分销佣金比例
export function addDistributionCommissionRate(data) {
  return request({
    url: '/distribution/distributionCommissionRate',
    method: 'post',
    data: data
  })
}

// 修改分销佣金比例
export function updateDistributionCommissionRate(data) {
  return request({
    url: '/distribution/distributionCommissionRate',
    method: 'put',
    data: data
  })
}

// 删除分销佣金比例
export function delDistributionCommissionRate(id) {
  return request({
    url: '/distribution/distributionCommissionRate/' + id,
    method: 'delete'
  })
}
