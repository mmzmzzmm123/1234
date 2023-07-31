import request from '@/utils/request'

// 查询隧道颜色列表
export function listColor(query) {
  return request({
    url: '/system/color/list',
    method: 'get',
    params: query
  })
}

// 查询隧道颜色详细
export function getColor(nid) {
  return request({
    url: '/system/color/' + nid,
    method: 'get'
  })
}

// 新增隧道颜色
export function addColor(data) {
  return request({
    url: '/system/color',
    method: 'post',
    data: data
  })
}

// 修改隧道颜色
export function updateColor(data) {
  return request({
    url: '/system/color',
    method: 'put',
    data: data
  })
}

// 删除隧道颜色
export function delColor(nid) {
  return request({
    url: '/system/color/' + nid,
    method: 'delete'
  })
}
