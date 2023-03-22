import request from '@/utils/request'

// 查询label列表
export function listLabel(query) {
  return request({
    url: '/course/label/list',
    method: 'get',
    params: query
  })
}

// 查询label详细
export function getLabel(id) {
  return request({
    url: '/course/label/' + id,
    method: 'get'
  })
}

// 新增label
export function addLabel(data) {
  return request({
    url: '/course/label',
    method: 'post',
    data: data
  })
}

// 修改label
export function updateLabel(data) {
  return request({
    url: '/course/label',
    method: 'put',
    data: data
  })
}

// 删除label
export function delLabel(id) {
  return request({
    url: '/course/label/' + id,
    method: 'delete'
  })
}
