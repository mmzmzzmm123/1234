import request from '@/utils/request'

// 查询微信注册用户信息列表
export function listMember(query) {
  return request({
    url: '/carpool/member/list',
    method: 'get',
    params: query
  })
}

// 查询微信注册用户信息详细
export function getMember(id) {
  return request({
    url: '/carpool/member/' + id,
    method: 'get'
  })
}

// 新增微信注册用户信息
export function addMember(data) {
  return request({
    url: '/carpool/member',
    method: 'post',
    data: data
  })
}

// 修改微信注册用户信息
export function updateMember(data) {
  return request({
    url: '/carpool/member',
    method: 'put',
    data: data
  })
}

// 删除微信注册用户信息
export function delMember(id) {
  return request({
    url: '/carpool/member/' + id,
    method: 'delete'
  })
}

// 导出微信注册用户信息
export function exportMember(query) {
  return request({
    url: '/carpool/member/export',
    method: 'get',
    params: query
  })
}