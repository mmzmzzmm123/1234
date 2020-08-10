import request from '@/utils/request'

// 查询儿童常规体检记录列表
export function listHealthcheck(query) {
  return request({
    url: '/benyi/healthcheck/list',
    method: 'get',
    params: query
  })
}

// 查询儿童常规体检记录详细
export function getHealthcheck(id) {
  return request({
    url: '/benyi/healthcheck/' + id,
    method: 'get'
  })
}

// 新增儿童常规体检记录
export function addHealthcheck(data) {
  return request({
    url: '/benyi/healthcheck',
    method: 'post',
    data: data
  })
}

// 修改儿童常规体检记录
export function updateHealthcheck(data) {
  return request({
    url: '/benyi/healthcheck',
    method: 'put',
    data: data
  })
}

// 删除儿童常规体检记录
export function delHealthcheck(id) {
  return request({
    url: '/benyi/healthcheck/' + id,
    method: 'delete'
  })
}

// 导出儿童常规体检记录
export function exportHealthcheck(query) {
  return request({
    url: '/benyi/healthcheck/export',
    method: 'get',
    params: query
  })
}