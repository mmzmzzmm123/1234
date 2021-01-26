import request from '@/utils/request'

// 查询客户健康列表
export function listHealthy(query) {
  return request({
    url: '/custom/healthy/list',
    method: 'get',
    params: query
  })
}

// 查询客户健康详细
export function getHealthy(id) {
  return request({
    url: '/custom/healthy/' + id,
    method: 'get'
  })
}

// 新增客户健康
export function addHealthy(data) {
  return request({
    url: '/custom/healthy',
    method: 'post',
    data: data
  })
}

// 修改客户健康
export function updateHealthy(data) {
  return request({
    url: '/custom/healthy',
    method: 'put',
    data: data
  })
}

// 删除客户健康
export function delHealthy(id) {
  return request({
    url: '/custom/healthy/' + id,
    method: 'delete'
  })
}

// 导出客户健康
export function exportHealthy(query) {
  return request({
    url: '/custom/healthy/export',
    method: 'get',
    params: query
  })
}

// 下载文件
export function download(fileName) {
  return request({
    url: '/common/download',
    method: 'get',
    params: {"fileName": fileName}
  })
}


