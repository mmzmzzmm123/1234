import request from '@/utils/request'

// 查询群体教师评选方案指标列表
export function listQtjspxfazbx(query) {
  return request({
    url: '/qtjs/qtjspxfazbx/list',
    method: 'get',
    params: query
  })
}

// 查询群体教师评选方案指标详细
export function getQtjspxfazbx(id) {
  return request({
    url: '/qtjs/qtjspxfazbx/' + id,
    method: 'get'
  })
}

// 新增群体教师评选方案指标
export function addQtjspxfazbx(data) {
  return request({
    url: '/qtjs/qtjspxfazbx',
    method: 'post',
    data: data
  })
}

// 修改群体教师评选方案指标
export function updateQtjspxfazbx(data) {
  return request({
    url: '/qtjs/qtjspxfazbx',
    method: 'put',
    data: data
  })
}

// 删除群体教师评选方案指标
export function delQtjspxfazbx(id) {
  return request({
    url: '/qtjs/qtjspxfazbx/' + id,
    method: 'delete'
  })
}

// 导出群体教师评选方案指标
export function exportQtjspxfazbx(query) {
  return request({
    url: '/qtjs/qtjspxfazbx/export',
    method: 'get',
    params: query
  })
}