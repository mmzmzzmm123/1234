import request from '@/utils/request'

// 查询公司管理列表
export function listCompany(query) {
  return request({
    url: '/base/company/list',
    method: 'get',
    params: query
  })
}

// 查询公司管理详细
export function getCompany(id) {
  return request({
    url: '/base/company/' + id,
    method: 'get'
  })
}

// 新增公司管理
export function addCompany(data) {
  return request({
    url: '/base/company',
    method: 'post',
    data: data
  })
}

// 修改公司管理
export function updateCompany(data) {
  return request({
    url: '/base/company',
    method: 'put',
    data: data
  })
}

// 公司状态修改
export function changeCompanyEnabled(id, enabled) {
  const data = {
    id,
    enabled
  }
  return request({
    url: '/base/company/changeEnabled',
    method: 'put',
    data: data
  })
}

// 删除公司管理
export function delCompany(id) {
  return request({
    url: '/base/company/' + id,
    method: 'delete'
  })
}

// 导出公司管理
export function exportCompany(query) {
  return request({
    url: '/base/company/export',
    method: 'get',
    params: query
  })
}
