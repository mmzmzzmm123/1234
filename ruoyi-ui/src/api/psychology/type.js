import request from '@/utils/request'

// 查询咨询类型列表
export function listType(query) {
  return request({
    url: '/psychology/type/list',
    method: 'get',
    params: query
  })
}

export function getTree() {
  return request({
    url: '/psychology/type/getTrees',
    method: 'get'
  })
}

// 查询咨询类型详细
export function getType(id) {
  return request({
    url: '/psychology/type/' + id,
    method: 'get'
  })
}

// 新增咨询类型
export function addType(data) {
  return request({
    url: '/psychology/type',
    method: 'post',
    data: data
  })
}

// 修改咨询类型
export function updateType(data) {
  return request({
    url: '/psychology/type',
    method: 'put',
    data: data
  })
}

// 删除咨询类型
export function delType(id) {
  return request({
    url: '/psychology/type/' + id,
    method: 'delete'
  })
}
