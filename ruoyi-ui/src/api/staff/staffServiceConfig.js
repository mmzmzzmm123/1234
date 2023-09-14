import request from '@/utils/request'

// 查询员工服务配置列表
export function listStaffServiceConfig(query) {
  return request({
    url: '/staff/staffServiceConfig/list',
    method: 'get',
    params: query
  })
}

// 查询员工服务配置详细
export function getStaffServiceConfig(id) {
  return request({
    url: '/staff/staffServiceConfig/' + id,
    method: 'get'
  })
}

// 新增员工服务配置
export function addStaffServiceConfig(data) {
  return request({
    url: '/staff/staffServiceConfig',
    method: 'post',
    data: data
  })
}

// 修改员工服务配置
export function updateStaffServiceConfig(data) {
  return request({
    url: '/staff/staffServiceConfig',
    method: 'put',
    data: data
  })
}

// 删除员工服务配置
export function delStaffServiceConfig(id) {
  return request({
    url: '/staff/staffServiceConfig/' + id,
    method: 'delete'
  })
}
