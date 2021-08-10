import request from '@/utils/request'

// 查询邀请列表
export function listInvite(query) {
  return request({
    url: '/system/invite/list',
    method: 'get',
    params: query
  })
}

// 查询邀请详细
export function getInvite(id) {
  return request({
    url: '/system/invite/' + id,
    method: 'get'
  })
}

// 新增邀请
export function addInvite(data) {
  return request({
    url: '/system/invite',
    method: 'post',
    data: data
  })
}

// 修改邀请
export function updateInvite(data) {
  return request({
    url: '/system/invite',
    method: 'put',
    data: data
  })
}

// 删除邀请
export function delInvite(id) {
  return request({
    url: '/system/invite/' + id,
    method: 'delete'
  })
}

// 导出邀请
export function exportInvite(query) {
  return request({
    url: '/system/invite/export',
    method: 'get',
    params: query
  })
}