import request from '@/utils/request'

// 查询书签_标签列表
export function listByUser(query) {
  return request({
    url: '/system/tag/listByUser',
    method: 'get',
    params: query
  })
}

// 查询书签_标签列表
export function listByUserLike(query) {
  return request({
    url: '/system/tag/listByUserLike',
    method: 'get',
    params: query
  })
}

// 新增书签管理
export function addTagByUser(data) {
  return request({
    url: '/system/tag/addByUser',
    method: 'post',
    data: data
  })
}

// 刪除书签
export function deleteTag(id) {
  return request({
    url: '/system/tag/deleteTag/' + id,
    method: 'get'
  })
}

// 修改书签管理
export function editByUser(data) {
  return request({
    url: '/system/tag/editByUser',
    method: 'post',
    data: data
  })
}
















// 查询书签_标签列表
export function listTag(query) {
  return request({
    url: '/system/tag/list',
    method: 'get',
    params: query
  })
}

// 查询书签_标签详细
export function getTag(id) {
  return request({
    url: '/system/tag/' + id,
    method: 'get'
  })
}

// 新增书签_标签
export function addTag(data) {
  return request({
    url: '/system/tag',
    method: 'post',
    data: data
  })
}

// 修改书签_标签
export function updateTag(data) {
  return request({
    url: '/system/tag',
    method: 'put',
    data: data
  })
}

// 删除书签_标签
export function delTag(id) {
  return request({
    url: '/system/tag/' + id,
    method: 'delete'
  })
}

// 导出书签_标签
export function exportTag(query) {
  return request({
    url: '/system/tag/export',
    method: 'get',
    params: query
  })
}
