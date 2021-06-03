import request from '@/utils/request'

// 查询群体教师考核文件管理列表
export function listQtjskhwj(query) {
  return request({
    url: '/qtjskhgl/qtjskhwj/list',
    method: 'get',
    params: query
  })
}

// 查询群体教师考核文件管理详细
export function getQtjskhwj(id) {
  return request({
    url: '/qtjskhgl/qtjskhwj/' + id,
    method: 'get'
  })
}

// 新增群体教师考核文件管理
export function addQtjskhwj(data) {
  return request({
    url: '/qtjskhgl/qtjskhwj',
    method: 'post',
    data: data
  })
}

// 修改群体教师考核文件管理
export function updateQtjskhwj(data) {
  return request({
    url: '/qtjskhgl/qtjskhwj',
    method: 'put',
    data: data
  })
}

// 删除群体教师考核文件管理
export function delQtjskhwj(id) {
  return request({
    url: '/qtjskhgl/qtjskhwj/' + id,
    method: 'delete'
  })
}

// 导出群体教师考核文件管理
export function exportQtjskhwj(query) {
  return request({
    url: '/qtjskhgl/qtjskhwj/export',
    method: 'get',
    params: query
  })
}

// 删除群体教师考核文件管理
export function downLoad(ids) {
  return request({
    url: '/qtjskhgl/qtjskhwj/download/' + ids,
    method: 'get'
  })
}