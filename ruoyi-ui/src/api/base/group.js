import request from '@/utils/request'

// 查询考勤组管理列表
export function listGroup(query) {
  return request({
    url: '/base/group/list',
    method: 'get',
    params: query
  })
}

// 查询考勤组管理详细
export function getGroup(id) {
  return request({
    url: '/base/group/' + id,
    method: 'get'
  })
}

// 新增考勤组管理
export function addGroup(data) {
  return request({
    url: '/base/group',
    method: 'post',
    data: data
  })
}

// 修改考勤组管理
export function updateGroup(data) {
  return request({
    url: '/base/group',
    method: 'put',
    data: data
  })
}

// 删除考勤组管理
export function delGroup(id) {
  return request({
    url: '/base/group/' + id,
    method: 'delete'
  })
}

// 导出考勤组管理
export function exportGroup(query) {
  return request({
    url: '/base/group/export',
    method: 'get',
    params: query
  })
}