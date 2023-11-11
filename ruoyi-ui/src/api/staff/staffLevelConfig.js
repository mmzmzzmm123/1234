import request from '@/utils/request'

// 查询员工等级配置列表
export function listStaffLevelConfig(query) {
  return request({
    url: '/staff/staffLevelConfig/list',
    method: 'get',
    params: query
  })
}

// 查询员工等级配置详细
export function getStaffLevelConfig(id) {
  return request({
    url: '/staff/staffLevelConfig/' + id,
    method: 'get'
  })
}

// 新增员工等级配置
export function addStaffLevelConfig(data) {
  return request({
    url: '/staff/staffLevelConfig',
    method: 'post',
    data: data
  })
}

// 修改员工等级配置
export function updateStaffLevelConfig(data) {
  return request({
    url: '/staff/staffLevelConfig',
    method: 'put',
    data: data
  })
}

// 删除员工等级配置
export function delStaffLevelConfig(id) {
  return request({
    url: '/staff/staffLevelConfig/' + id,
    method: 'delete'
  })
}
