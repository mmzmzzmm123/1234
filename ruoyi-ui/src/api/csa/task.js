import request from '@/utils/request'

// 查询打理任务列表
export function listTask(query) {
  return request({
    url: '/csa/task/list',
    method: 'get',
    params: query
  })
}

// 查询打理任务详细
export function getTask(id) {
  return request({
    url: '/csa/task/' + id,
    method: 'get'
  })
}

// 新增打理任务
export function addTask(data) {
  return request({
    url: '/csa/task',
    method: 'post',
    data: data
  })
}

// 修改打理任务
export function updateTask(data) {
  return request({
    url: '/csa/task',
    method: 'put',
    data: data
  })
}

// 删除打理任务
export function delTask(id) {
  return request({
    url: '/csa/task/' + id,
    method: 'delete'
  })
}
