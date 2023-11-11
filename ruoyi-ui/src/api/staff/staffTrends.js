import request from '@/utils/request'

// 查询员工动态列表
export function listStaffTrends(query) {
  return request({
    url: '/staff/staffTrends/list',
    method: 'get',
    params: query
  })
}

// 查询员工动态详细
export function getStaffTrends(id) {
  return request({
    url: '/staff/staffTrends/' + id,
    method: 'get'
  })
}

// 新增员工动态
export function addStaffTrends(data) {
  return request({
    url: '/staff/staffTrends',
    method: 'post',
    data: data
  })
}

// 修改员工动态
export function updateStaffTrends(data) {
  return request({
    url: '/staff/staffTrends',
    method: 'put',
    data: data
  })
}

// 删除员工动态
export function delStaffTrends(id) {
  return request({
    url: '/staff/staffTrends/' + id,
    method: 'delete'
  })
}
