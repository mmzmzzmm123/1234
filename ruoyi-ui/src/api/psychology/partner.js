import request from '@/utils/request'

// 查询咨询师入驻申请列表
export function listPartner(query) {
  return request({
    url: '/psychology/partner/list',
    method: 'get',
    params: query
  })
}

// 查询咨询师入驻申请详细
export function getPartner(id) {
  return request({
    url: '/psychology/partner/' + id,
    method: 'get'
  })
}

// 修改咨询师入驻申请
export function updatePartner(data) {
  return request({
    url: '/psychology/partner',
    method: 'put',
    data: data
  })
}

// 新增咨询服务配置
export function createUser(id) {
  return request({
    url: '/psychology/partner/createUser/' + id,
    method: 'post',
  })
}

// 删除咨询师入驻申请
export function delPartner(id) {
  return request({
    url: '/psychology/partner/' + id,
    method: 'delete'
  })
}
