import request from '@/utils/request'

// 查询幼儿考勤列表
export function listDetail(query) {
  return request({
    url: '/benyi/checkindetail/list',
    method: 'get',
    params: query
  })
}

// 查询幼儿考勤详细
export function getDetail(id) {
  return request({
    url: '/benyi/checkindetail/' + id,
    method: 'get'
  })
}

// 新增幼儿考勤
export function addDetail(data) {
  return request({
    url: '/benyi/checkindetail',
    method: 'post',
    data: data
  })
}

// 修改幼儿考勤
export function updateDetail(data) {
  return request({
    url: '/benyi/checkindetail',
    method: 'put',
    data: data
  })
}

// 删除幼儿考勤
export function delDetail(id) {
  return request({
    url: '/benyi/checkindetail/' + id,
    method: 'delete'
  })
}

// 导出幼儿考勤
export function exportDetail(query) {
  return request({
    url: '/benyi/checkindetail/export',
    method: 'get',
    params: query
  })
}

// 查询幼儿考勤列表
export function listDatetime(query) {
  return request({
    url: '/benyi/datetime/list',
    method: 'get',
    params: query
  })
}