import request from '@/utils/request'

// 查询菜地日志列表
export function listLog(query) {
  return request({
    url: '/csa/log/list',
    method: 'get',
    params: query
  })
}

// 查询菜地日志详细
export function getLog(id) {
  return request({
    url: '/csa/log/' + id,
    method: 'get'
  })
}

// 新增菜地日志
export function addLog(data) {
  return request({
    url: '/csa/log',
    method: 'post',
    data: data
  })
}

// 修改菜地日志
export function updateLog(data) {
  return request({
    url: '/csa/log',
    method: 'put',
    data: data
  })
}

// 删除菜地日志
export function delLog(id) {
  return request({
    url: '/csa/log/' + id,
    method: 'delete'
  })
}
