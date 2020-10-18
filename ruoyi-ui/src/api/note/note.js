import request from '@/utils/request'

// 查询便签管理列表
export function selectBymenuNote(query) {
  return request({
    url: '/note/note/selectBymenuNote',
    method: 'get',
    params: query
  })
}

// 查询便签管理详细
export function userGetNoteInfo(noteId) {
  return request({
    url: '/note/note/userGetNoteInfo/' + noteId,
    method: 'get'
  })
}



// 新增便签管理
export function addUserNote(data) {
  return request({
    url: '/note/note/addUserNote',
    method: 'post',
    data: data
  })
}



// 修改便签文章的内容 实时保存
export function userUpdateNote(data) {
  return request({
    url: '/note/note/userUpdateNote',
    method: 'post',
    data: data
  })
}


// 查询便签管理列表
export function listNote(query) {
  return request({
    url: '/note/note/list',
    method: 'get',
    params: query
  })
}

// 查询便签管理详细
export function getNote(noteId) {
  return request({
    url: '/note/note/' + noteId,
    method: 'get'
  })
}

// 新增便签管理
export function addNote(data) {
  return request({
    url: '/note/note',
    method: 'post',
    data: data
  })
}

// 修改便签管理
export function updateNote(data) {
  return request({
    url: '/note/note',
    method: 'put',
    data: data
  })
}

// 删除便签管理
export function delNote(noteId) {
  return request({
    url: '/note/note/' + noteId,
    method: 'delete'
  })
}

// 导出便签管理
export function exportNote(query) {
  return request({
    url: '/note/note/export',
    method: 'get',
    params: query
  })
}
