import request from '@/utils/request'

// 查询园所收费标准列表
export function listSchoolcharge(query) {
  return request({
    url: '/benyi/schoolcharge/list',
    method: 'get',
    params: query
  })
}

// 查询幼儿收费列表
export function listChildcharge(query) {
  return request({
    url: '/benyi/schoolcharge/child/list',
    method: 'get',
    params: query
  })
}

// 查询园所收费标准详细
export function getSchoolcharge(id) {
  return request({
    url: '/benyi/schoolcharge/' + id,
    method: 'get'
  })
}

// 新增园所收费标准
export function addSchoolcharge(data) {
  return request({
    url: '/benyi/schoolcharge',
    method: 'post',
    data: data
  })
}

// 修改园所收费标准
export function updateSchoolcharge(data) {
  return request({
    url: '/benyi/schoolcharge',
    method: 'put',
    data: data
  })
}

// 删除园所收费标准
export function delSchoolcharge(id) {
  return request({
    url: '/benyi/schoolcharge/' + id,
    method: 'delete'
  })
}

// 导出园所收费标准
export function exportSchoolcharge(query) {
  return request({
    url: '/benyi/schoolcharge/export',
    method: 'get',
    params: query
  })
}
