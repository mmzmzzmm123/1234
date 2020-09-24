import request from '@/utils/request'

// 查询群体教师评选结果列表
export function listQtjspxjg(query) {
  return request({
    url: '/qtjs/qtjspxjg/list',
    method: 'get',
    params: query
  })
}

// 查询群体教师评选结果详细
export function getQtjspxjg(id) {
  return request({
    url: '/qtjs/qtjspxjg/' + id,
    method: 'get'
  })
}

// 新增群体教师评选结果
export function addQtjspxjg(data) {
  return request({
    url: '/qtjs/qtjspxjg',
    method: 'post',
    data: data
  })
}

// 修改群体教师评选结果
export function updateQtjspxjg(data) {
  return request({
    url: '/qtjs/qtjspxjg',
    method: 'put',
    data: data
  })
}

// 删除群体教师评选结果
export function delQtjspxjg(id) {
  return request({
    url: '/qtjs/qtjspxjg/' + id,
    method: 'delete'
  })
}

// 导出群体教师评选结果
export function exportQtjspxjg(query) {
  return request({
    url: '/qtjs/qtjspxjg/export',
    method: 'get',
    params: query
  })
}