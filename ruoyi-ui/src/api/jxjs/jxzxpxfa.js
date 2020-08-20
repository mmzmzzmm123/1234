import request from '@/utils/request'

// 查询见习之星评选方案列表
export function listJxzxpxfa(query) {
  return request({
    url: '/jxjs/jxzxpxfa/list',
    method: 'get',
    params: query
  })
}

// 查询见习之星评选方案详细
export function getJxzxpxfa(id) {
  return request({
    url: '/jxjs/jxzxpxfa/' + id,
    method: 'get'
  })
}

// 新增见习之星评选方案
export function addJxzxpxfa(data) {
  return request({
    url: '/jxjs/jxzxpxfa',
    method: 'post',
    data: data
  })
}

// 修改见习之星评选方案
export function updateJxzxpxfa(data) {
  return request({
    url: '/jxjs/jxzxpxfa',
    method: 'put',
    data: data
  })
}

// 删除见习之星评选方案
export function delJxzxpxfa(id) {
  return request({
    url: '/jxjs/jxzxpxfa/' + id,
    method: 'delete'
  })
}

// 导出见习之星评选方案
export function exportJxzxpxfa(query) {
  return request({
    url: '/jxjs/jxzxpxfa/export',
    method: 'get',
    params: query
  })
}

// 修改方案状态
export function changeStatus(id, fazt) {
  const data = {
    id,
    fazt
  }
  return request({
    url: '/jxjs/jxzxpxfa/changeStatus',
    method: 'put',
    data: data
  })
}