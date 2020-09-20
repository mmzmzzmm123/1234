import request from '@/utils/request'

// 查询share书签分享列表
export function listShare(query) {
  return request({
    url: '/note/share/list',
    method: 'get',
    params: query
  })
}

// 查询share书签分享详细
export function getShare(noteId) {
  return request({
    url: '/note/share/' + noteId,
    method: 'get'
  })
}

// 新增share书签分享
export function addShare(data) {
  return request({
    url: '/note/share',
    method: 'post',
    data: data
  })
}

// 修改share书签分享
export function updateShare(data) {
  return request({
    url: '/note/share',
    method: 'put',
    data: data
  })
}

// 删除share书签分享
export function delShare(noteId) {
  return request({
    url: '/note/share/' + noteId,
    method: 'delete'
  })
}

// 导出share书签分享
export function exportShare(query) {
  return request({
    url: '/note/share/export',
    method: 'get',
    params: query
  })
}