import request from '@/utils/request'

// 查询咨询banner配置列表
export function getList(query) {
  return request({
    url: '/psychology/column/list',
    method: 'get',
    params: query
  })
}

// 查询咨询banner配置详细
export function getOne(id) {
  return request({
    url: '/psychology/column/' + id,
    method: 'get'
  })
}

// 新增咨询banner配置
export function add(data) {
  return request({
    url: '/psychology/column',
    method: 'post',
    data: data
  })
}

// 修改咨询banner配置
export function update(data) {
  return request({
    url: '/psychology/column',
    method: 'put',
    data: data
  })
}

// 删除咨询banner配置
export function del(id) {
  return request({
    url: '/psychology/column/' + id,
    method: 'delete',
    data: id
  })
}
