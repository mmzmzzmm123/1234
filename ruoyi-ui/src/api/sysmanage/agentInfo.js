import request from '@/utils/request'

// 查询经销商档案列表
export function listAgentInfo(query) {
  return request({
    url: '/sysmanage/agentInfo/list',
    method: 'get',
    params: query
  })
}

// 查询经销商档案详细
export function getAgentInfo(id) {
  return request({
    url: '/sysmanage/agentInfo/' + id,
    method: 'get'
  })
}

// 新增经销商档案
export function addAgentInfo(data) {
  return request({
    url: '/sysmanage/agentInfo',
    method: 'post',
    data: data
  })
}

// 修改经销商档案
export function updateAgentInfo(data) {
  return request({
    url: '/sysmanage/agentInfo',
    method: 'put',
    data: data
  })
}

// 删除经销商档案
export function delAgentInfo(id) {
  return request({
    url: '/sysmanage/agentInfo/' + id,
    method: 'delete'
  })
}
