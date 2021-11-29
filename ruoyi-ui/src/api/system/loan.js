import request from '@/utils/request'

// 查询企业贷款信息列表
export function listLoan(query) {
  return request({
    url: '/system/loan/list',
    method: 'get',
    params: query
  })
}

// 查询企业贷款信息详细
export function getLoan(companyId) {
  return request({
    url: '/system/loan/' + companyId,
    method: 'get'
  })
}

// 新增企业贷款信息
export function addLoan(data) {
  return request({
    url: '/system/loan',
    method: 'post',
    data: data
  })
}

// 修改企业贷款信息
export function updateLoan(data) {
  return request({
    url: '/system/loan',
    method: 'put',
    data: data
  })
}

// 删除企业贷款信息
export function delLoan(companyId) {
  return request({
    url: '/system/loan/' + companyId,
    method: 'delete'
  })
}

// 导出企业贷款信息
export function exportLoan(query) {
  return request({
    url: '/system/loan/export',
    method: 'get',
    params: query
  })
}

// 查询企业贷款信息详细
export function getReport(tyshxydm) {
  return request({
    url: '/system/loan/report/' + tyshxydm,
    method: 'get'
  })
}