import request from '@/utils/request'

// 查询全景图管理列表
export function listInfo(query) {
  return request({
    url: '/system/info/list',
    method: 'get',
    params: query
  })
}

// 查询全景图管理详细
export function getInfo(id) {
  return request({
    url: '/system/info/' + id,
    method: 'get'
  })
}

// 新增全景图管理
export function addInfo(data) {
  return request({
    url: '/system/info',
    method: 'post',
    data: data
  })
}

// 修改全景图管理
export function updateInfo(data) {
  return request({
    url: '/system/info',
    method: 'put',
    data: data
  })
}

// 删除全景图管理
export function delInfo(id) {
  return request({
    url: '/system/info/' + id,
    method: 'delete'
  })
}

// 导出全景图管理
export function exportInfo(query) {
  return request({
    url: '/system/info/export',
    method: 'get',
    params: query
  })
}