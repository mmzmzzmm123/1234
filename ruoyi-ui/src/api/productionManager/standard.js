import request from '@/utils/request'

// 查询试验标准管理列表
export function listStandard(query) {
  return request({
    url: '/productionManager/standard/list',
    method: 'get',
    params: query
  })
}

// 查询试验标准管理详细
export function getStandard(standardId) {
  return request({
    url: '/productionManager/standard/' + standardId,
    method: 'get'
  })
}

// 新增试验标准管理
export function addStandard(data) {
  return request({
    url: '/productionManager/standard',
    method: 'post',
    data: data
  })
}

// 修改试验标准管理
export function updateStandard(data) {
  return request({
    url: '/productionManager/standard',
    method: 'put',
    data: data
  })
}

// 删除试验标准管理
export function delStandard(standardId) {
  return request({
    url: '/productionManager/standard/' + standardId,
    method: 'delete'
  })
}

// 导出试验标准管理
export function exportStandard(query) {
  return request({
    url: '/productionManager/standard/export',
    method: 'get',
    params: query
  })
}