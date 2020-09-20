import request from '@/utils/request'

// 查询便签的标签列表
export function listTag(query) {
  return request({
    url: '/note/tag/list',
    method: 'get',
    params: query
  })
}

// 查询便签的标签详细
export function getTag(noteTagId) {
  return request({
    url: '/note/tag/' + noteTagId,
    method: 'get'
  })
}

// 新增便签的标签
export function addTag(data) {
  return request({
    url: '/note/tag',
    method: 'post',
    data: data
  })
}

// 修改便签的标签
export function updateTag(data) {
  return request({
    url: '/note/tag',
    method: 'put',
    data: data
  })
}

// 删除便签的标签
export function delTag(noteTagId) {
  return request({
    url: '/note/tag/' + noteTagId,
    method: 'delete'
  })
}

// 导出便签的标签
export function exportTag(query) {
  return request({
    url: '/note/tag/export',
    method: 'get',
    params: query
  })
}