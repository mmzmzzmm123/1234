import request from '@/utils/request'

// 查询书签管理列表
export function listBookmark(query) {
  return request({
    url: '/bookmark/bookmark/list',
    method: 'get',
    params: query
  })
}

// 查询书签管理详细
export function getBookmark(bookmarkId) {
  return request({
    url: '/bookmark/bookmark/' + bookmarkId,
    method: 'get'
  })
}

// 新增书签管理
export function addBookmark(data) {
  return request({
    url: '/bookmark/bookmark',
    method: 'post',
    data: data
  })
}

// 修改书签管理
export function updateBookmark(data) {
  return request({
    url: '/bookmark/bookmark',
    method: 'put',
    data: data
  })
}

// 删除书签管理
export function delBookmark(bookmarkId) {
  return request({
    url: '/bookmark/bookmark/' + bookmarkId,
    method: 'delete'
  })
}

// 导出书签管理
export function exportBookmark(query) {
  return request({
    url: '/bookmark/bookmark/export',
    method: 'get',
    params: query
  })
}