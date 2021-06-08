import request from '@/utils/request'

// 查询进粉统计列表
export function listFanStatistics(query) {
  return request({
    url: '/custom/fanStatistics/list',
    method: 'get',
    params: query
  })
}

// 查询进粉统计详细
export function getFanStatistics(id) {
  return request({
    url: '/custom/fanStatistics/' + id,
    method: 'get'
  })
}

// 新增进粉统计
export function addFanStatistics(data) {
  return request({
    url: '/custom/fanStatistics',
    method: 'post',
    data: data
  })
}

// 修改进粉统计
export function updateFanStatistics(data) {
  return request({
    url: '/custom/fanStatistics',
    method: 'put',
    data: data
  })
}

// 删除进粉统计
export function delFanStatistics(id) {
  return request({
    url: '/custom/fanStatistics/' + id,
    method: 'delete'
  })
}

// 导出进粉统计
export function exportFanStatistics(query) {
  return request({
    url: '/custom/fanStatistics/export',
    method: 'get',
    params: query
  })
}

// 获取
export function getWxByUserId(userId) {
  return request({
    url: '/custom/fanStatistics/getWxByUserId',
    method: 'get',
    params: {"userId": userId}
  })
}

// 获取通过率
export function getImportFanPassRate(data) {
  return request({
    url: '/custom/fanStatistics/getImportFanPassRate',
    method: 'get',
    params: data
  })
}




