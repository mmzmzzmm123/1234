import request from '@/utils/request'

// 查询分销佣金记录列表
export function listDistributionCommissionRecord(query) {
  return request({
    url: '/distribution/distributionCommissionRecord/list',
    method: 'get',
    params: query
  })
}

// 查询分销佣金记录详细
export function getDistributionCommissionRecord(id) {
  return request({
    url: '/distribution/distributionCommissionRecord/' + id,
    method: 'get'
  })
}

// 新增分销佣金记录
export function addDistributionCommissionRecord(data) {
  return request({
    url: '/distribution/distributionCommissionRecord',
    method: 'post',
    data: data
  })
}

// 修改分销佣金记录
export function updateDistributionCommissionRecord(data) {
  return request({
    url: '/distribution/distributionCommissionRecord',
    method: 'put',
    data: data
  })
}

// 删除分销佣金记录
export function delDistributionCommissionRecord(id) {
  return request({
    url: '/distribution/distributionCommissionRecord/' + id,
    method: 'delete'
  })
}
