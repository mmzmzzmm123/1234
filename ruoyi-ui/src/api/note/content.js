import request from '@/utils/request'

// 查询文章内容列表
export function listContent(query) {
  return request({
    url: '/note/content/list',
    method: 'get',
    params: query
  })
}

// 查询文章内容详细
export function getContent(noteId) {
  return request({
    url: '/note/content/' + noteId,
    method: 'get'
  })
}

// 新增文章内容
export function addContent(data) {
  return request({
    url: '/note/content',
    method: 'post',
    data: data
  })
}

// 修改文章内容
export function updateContent(data) {
  return request({
    url: '/note/content',
    method: 'put',
    data: data
  })
}

// 删除文章内容
export function delContent(noteId) {
  return request({
    url: '/note/content/' + noteId,
    method: 'delete'
  })
}

// 导出文章内容
export function exportContent(query) {
  return request({
    url: '/note/content/export',
    method: 'get',
    params: query
  })
}