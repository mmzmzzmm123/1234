import request from '@/utils/request'

// 查询测评标签列表
export function listLabel(query) {
  return request({
    url: '/gauge/label/list',
    method: 'get',
    params: query
  })
}

// 查询测评标签详细
export function getLabel(id) {
  return request({
    url: '/gauge/label/' + id,
    method: 'get'
  })
}

// 新增测评标签
export function addLabel(data) {
  return request({
    url: '/gauge/label',
    method: 'post',
    data: data
  })
}

// 修改测评标签
export function updateLabel(data) {
  return request({
    url: '/gauge/label',
    method: 'put',
    data: data
  })
}

// 删除测评标签
export function delLabel(id) {
  return request({
    url: '/gauge/label/' + id,
    method: 'delete'
  })
}
