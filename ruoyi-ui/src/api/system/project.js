import request from '@/utils/request'

// 查询角色列表
export function listProject(query) {
  return request({
    url: '/system/project/list',
    method: 'get',
    params: query
  })
}

// 查询角色详细
export function getProject(projectId) {
  return request({
    url: '/system/project/' + projectId,
    method: 'get'
  })
}

// 新增角色
export function addProject(data) {
  return request({
    url: '/system/project',
    method: 'post',
    data: data
  })
}

// 修改角色
export function updateProject(data) {
  return request({
    url: '/system/project',
    method: 'put',
    data: data
  })
}

// 角色数据权限
export function dataScope(data) {
  return request({
    url: '/system/project/dataScope',
    method: 'put',
    data: data
  })
}

// 角色状态修改
export function changeProjectStatus(projectId, status) {
  const data = {
    projectId,
    status
  }
  return request({
    url: '/system/project/changeStatus',
    method: 'put',
    data: data
  })
}

// 删除角色
export function delProject(projectId) {
  return request({
    url: '/system/project/' + projectId,
    method: 'delete'
  })
}

// 导出角色
export function exportProject(query) {
  return request({
    url: '/system/project/export',
    method: 'get',
    params: query
  })
}