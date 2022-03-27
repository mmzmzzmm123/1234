import request from '@/utils/request'

// 查询拱棚使用记录列表
export function listArchedrecord(query) {
  return request({
    url: '/csa/archedrecord/list',
    method: 'get',
    params: query
  })
}

// 查询拱棚使用记录详细
export function getArchedrecord(id) {
  return request({
    url: '/csa/archedrecord/' + id,
    method: 'get'
  })
}

// 新增拱棚使用记录
export function addArchedrecord(data) {
  return request({
    url: '/csa/archedrecord',
    method: 'post',
    data: data
  })
}

// 修改拱棚使用记录
export function updateArchedrecord(data) {
  return request({
    url: '/csa/archedrecord',
    method: 'put',
    data: data
  })
}

// 删除拱棚使用记录
export function delArchedrecord(id) {
  return request({
    url: '/csa/archedrecord/' + id,
    method: 'delete'
  })
}
