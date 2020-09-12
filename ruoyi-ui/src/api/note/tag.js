import request from '@/utils/request'

// 查询便签标签列表
export function listTag(query) {
  return request({
    url: '/note/tag/list',
    method: 'get',
    params: query
  })
}

// 查询便签标签详细
export function getTag(id) {
  return request({
    url: '/note/tag/' + id,
    method: 'get'
  })
}

// 新增便签标签
export function addTag(data) {
  return request({
    url: '/note/tag',
    method: 'post',
    data: data
  })
}

// 修改便签标签
export function updateTag(data) {
  return request({
    url: '/note/tag',
    method: 'put',
    data: data
  })
}

// 删除便签标签
export function delTag(id) {
  return request({
    url: '/note/tag/' + id,
    method: 'delete'
  })
}

// 导出便签标签
export function exportTag(query) {
  return request({
    url: '/note/tag/export',
    method: 'get',
    params: query
  })
}