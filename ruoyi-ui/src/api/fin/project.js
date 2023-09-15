import request from '@/utils/request'

// 查询记账项目列表
export function listProject(query) {
  return request({
    url: '/fin/project/list',
    method: 'get',
    params: query
  })
}

// 查询记账项目详细
export function getProject(projId) {
  return request({
    url: '/fin/project/' + projId,
    method: 'get'
  })
}

// 新增记账项目
export function addProject(data) {
  return request({
    url: '/fin/project',
    method: 'post',
    data: data
  })
}

// 修改记账项目
export function updateProject(data) {
  return request({
    url: '/fin/project',
    method: 'put',
    data: data
  })
}

// 删除记账项目
export function delProject(projId) {
  return request({
    url: '/fin/project/' + projId,
    method: 'delete'
  })
}
