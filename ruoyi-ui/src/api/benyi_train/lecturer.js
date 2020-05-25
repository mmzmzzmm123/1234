import request from '@/utils/request'

// 查询讲师列表
export function listLecturer(query) {
  return request({
    url: '/benyi/lecturer/list',
    method: 'get',
    params: query
  })
}

// 查询讲师详细
export function getLecturer(id) {
  return request({
    url: '/benyi/lecturer/' + id,
    method: 'get'
  })
}

// 新增讲师
export function addLecturer(data) {
  return request({
    url: '/benyi/lecturer',
    method: 'post',
    data: data
  })
}

// 修改讲师
export function updateLecturer(data) {
  return request({
    url: '/benyi/lecturer',
    method: 'put',
    data: data
  })
}

// 删除讲师
export function delLecturer(id) {
  return request({
    url: '/benyi/lecturer/' + id,
    method: 'delete'
  })
}

// 导出讲师
export function exportLecturer(query) {
  return request({
    url: '/benyi/lecturer/export',
    method: 'get',
    params: query
  })
}