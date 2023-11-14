import request from '@/utils/request'

// 查询tag信息列表
export function listInfo(query) {
  return request({
    url: '/system/taginfo/list',
    method: 'get',
    params: query
  })
}

// 查询tag信息详细
export function getInfo(tagInfoId) {
  return request({
    url: '/system/taginfo/' + tagInfoId,
    method: 'get'
  })
}

// 新增tag信息
export function addInfo(data) {
  return request({
    url: '/system/taginfo',
    method: 'post',
    data: data
  })
}

// 修改tag信息
export function updateInfo(data) {
  return request({
    url: '/system/taginfo',
    method: 'put',
    data: data
  })
}

// 删除tag信息
export function delInfo(tagInfoId) {
  return request({
    url: '/system/taginfo/' + tagInfoId,
    method: 'delete'
  })
}
