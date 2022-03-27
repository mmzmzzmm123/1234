import request from '@/utils/request'

// 查询沟通记录列表
export function listCommrecord(query) {
  return request({
    url: '/csa/commrecord/list',
    method: 'get',
    params: query
  })
}

// 查询沟通记录详细
export function getCommrecord(id) {
  return request({
    url: '/csa/commrecord/' + id,
    method: 'get'
  })
}

// 新增沟通记录
export function addCommrecord(data) {
  return request({
    url: '/csa/commrecord',
    method: 'post',
    data: data
  })
}

// 修改沟通记录
export function updateCommrecord(data) {
  return request({
    url: '/csa/commrecord',
    method: 'put',
    data: data
  })
}

// 删除沟通记录
export function delCommrecord(id) {
  return request({
    url: '/csa/commrecord/' + id,
    method: 'delete'
  })
}
