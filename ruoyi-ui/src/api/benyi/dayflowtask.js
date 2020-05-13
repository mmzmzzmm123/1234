import request from '@/utils/request'

// 查询一日流程任务列表
export function listDayflowtask(query) {
  return request({
    url: '/benyi/dayflowtask/list',
    method: 'get',
    params: query
  })
}

// 查询一日流程任务详细
export function getDayflowtask(id) {
  return request({
    url: '/benyi/dayflowtask/' + id,
    method: 'get'
  })
}

// 新增一日流程任务
export function addDayflowtask(data) {
  return request({
    url: '/benyi/dayflowtask',
    method: 'post',
    data: data
  })
}

// 修改一日流程任务
export function updateDayflowtask(data) {
  return request({
    url: '/benyi/dayflowtask',
    method: 'put',
    data: data
  })
}

// 删除一日流程任务
export function delDayflowtask(id) {
  return request({
    url: '/benyi/dayflowtask/' + id,
    method: 'delete'
  })
}

// 导出一日流程任务
export function exportDayflowtask(query) {
  return request({
    url: '/benyi/dayflowtask/export',
    method: 'get',
    params: query
  })
}