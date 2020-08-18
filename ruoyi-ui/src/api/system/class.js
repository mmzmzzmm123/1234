import request from '@/utils/request'

// 查询班级信息列表
export function listClass(query) {
  return request({
    url: '/system/class/list',
    method: 'get',
    params: query
  })
}

// 查询班级信息详细
export function getClass(bjbh) {
  return request({
    url: '/system/class/' + bjbh,
    method: 'get'
  })
}

// 新增班级信息
export function addClass(data) {
  return request({
    url: '/system/class',
    method: 'post',
    data: data
  })
}

// 修改班级信息
export function updateClass(data) {
  return request({
    url: '/system/class',
    method: 'put',
    data: data
  })
}

// 删除班级信息
export function delClass(bjbh) {
  return request({
    url: '/system/class/' + bjbh,
    method: 'delete'
  })
}

// 清空班级教师信息
export function delJsClass(bjbh) {
  return request({
    url: '/system/class/deljs/' + bjbh,
    method: 'delete'
  })
}

// 导出班级信息
export function exportClass(query) {
  return request({
    url: '/system/class/export',
    method: 'get',
    params: query
  })
}