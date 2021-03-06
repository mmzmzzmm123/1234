import request from '@/utils/request'

// 查询模板维护列表
export function listPreserve(query) {
  return request({
    url: '/basic/preserve/list',
    method: 'get',
    params: query
  })
}

// 查询模板维护详细
export function getPreserve(id) {
  return request({
    url: '/basic/preserve/' + id,
    method: 'get'
  })
}

// 新增模板维护
export function addPreserve(data) {
  return request({
    url: '/basic/preserve',
    method: 'post',
    data: data
  })
}

// 修改模板维护
export function updatePreserve(data) {
  return request({
    url: '/basic/preserve',
    method: 'put',
    data: data
  })
}

// 删除模板维护
export function delPreserve(id) {
  return request({
    url: '/basic/preserve/' + id,
    method: 'delete'
  })
}

// 导出模板维护
export function exportPreserve(query) {
  return request({
    url: '/basic/preserve/export',
    method: 'get',
    params: query
  })
}