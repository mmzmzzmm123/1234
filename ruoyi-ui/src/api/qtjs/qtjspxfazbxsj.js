import request from '@/utils/request'

// 查询群体教师评选指标数据列表
export function listQtjspxfazbxsj(query) {
  return request({
    url: '/qtjs/qtjspxfazbxsj/list',
    method: 'get',
    params: query
  })
}

// 查询群体教师评选指标数据详细
export function getQtjspxfazbxsj(id) {
  return request({
    url: '/qtjs/qtjspxfazbxsj/' + id,
    method: 'get'
  })
}

// 新增群体教师评选指标数据
export function addQtjspxfazbxsj(data) {
  return request({
    url: '/qtjs/qtjspxfazbxsj',
    method: 'post',
    data: data
  })
}

// 修改群体教师评选指标数据
export function updateQtjspxfazbxsj(data) {
  return request({
    url: '/qtjs/qtjspxfazbxsj',
    method: 'put',
    data: data
  })
}

// 删除群体教师评选指标数据
export function delQtjspxfazbxsj(id) {
  return request({
    url: '/qtjs/qtjspxfazbxsj/' + id,
    method: 'delete'
  })
}

// 导出群体教师评选指标数据
export function exportQtjspxfazbxsj(query) {
  return request({
    url: '/qtjs/qtjspxfazbxsj/export',
    method: 'get',
    params: query
  })
}