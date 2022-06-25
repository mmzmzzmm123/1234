import request from '@/utils/request'

// 查询API列表
export function listKey(query) {
  return request({
    url: '/udef/key/list',
    method: 'get',
    params: query
  })
}

// 查询API详细
export function getKey(id) {
  return request({
    url: '/udef/key/' + id,
    method: 'get'
  })
}

// 新增API
export function addKey(data) {
  return request({
    url: '/udef/key',
    method: 'post',
    data: data
  })
}

// 修改API
export function updateKey(data) {
  return request({
    url: '/udef/key',
    method: 'put',
    data: data
  })
}

// 删除API
export function delKey(id) {
  return request({
    url: '/udef/key/' + id,
    method: 'delete'
  })
}
