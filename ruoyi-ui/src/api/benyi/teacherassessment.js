import request from '@/utils/request'

// 查询教师月绩效考核列表
export function listTeacherassessment(query) {
  return request({
    url: '/benyi/teacherassessment/list',
    method: 'get',
    params: query
  })
}

// 查询教师月绩效考核详细
export function getTeacherassessment(id) {
  return request({
    url: '/benyi/teacherassessment/' + id,
    method: 'get'
  })
}

// 新增教师月绩效考核
export function addTeacherassessment(data) {
  return request({
    url: '/benyi/teacherassessment',
    method: 'post',
    data: data
  })
}

// 修改教师月绩效考核
export function updateTeacherassessment(data) {
  return request({
    url: '/benyi/teacherassessment',
    method: 'put',
    data: data
  })
}

// 删除教师月绩效考核
export function delTeacherassessment(id) {
  return request({
    url: '/benyi/teacherassessment/' + id,
    method: 'delete'
  })
}

// 导出教师月绩效考核
export function exportTeacherassessment(query) {
  return request({
    url: '/benyi/teacherassessment/export',
    method: 'get',
    params: query
  })
}