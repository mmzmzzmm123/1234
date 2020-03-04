import request from '@/utils/request'

// 查询成语存放列表
export function listIdiom(query) {
  return request({
    url: '/base/idiom/list',
    method: 'get',
    params: query
  })
}

// 查询成语存放详细
export function getIdiom(id) {
  return request({
    url: '/base/idiom/' + id,
    method: 'get'
  })
}

// 新增成语存放
export function addIdiom(data) {
  return request({
    url: '/base/idiom',
    method: 'post',
    data: data
  })
}

// 修改成语存放
export function updateIdiom(data) {
  return request({
    url: '/base/idiom',
    method: 'put',
    data: data
  })
}

// 删除成语存放
export function delIdiom(id) {
  return request({
    url: '/base/idiom/' + id,
    method: 'delete'
  })
}

// 导出成语存放
export function exportIdiom(query) {
  return request({
    url: '/base/idiom/export',
    method: 'get',
    params: query
  })
}