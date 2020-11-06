import request from '@/utils/request'

// 查询幼儿评估记录列表
export function listAssessmentrecordchild(query) {
  return request({
    url: '/benyi/assessmentrecordchild/list',
    method: 'get',
    params: query
  })
}

// 查询幼儿评估记录详细
export function getAssessmentrecordchild(id) {
  return request({
    url: '/benyi/assessmentrecordchild/' + id,
    method: 'get'
  })
}

// 新增幼儿评估记录
export function addAssessmentrecordchild(data) {
  return request({
    url: '/benyi/assessmentrecordchild',
    method: 'post',
    data: data
  })
}

// 修改幼儿评估记录
export function updateAssessmentrecordchild(data) {
  return request({
    url: '/benyi/assessmentrecordchild',
    method: 'put',
    data: data
  })
}

// 删除幼儿评估记录
export function delAssessmentrecordchild(id) {
  return request({
    url: '/benyi/assessmentrecordchild/' + id,
    method: 'delete'
  })
}

// 导出幼儿评估记录
export function exportAssessmentrecordchild(query) {
  return request({
    url: '/benyi/assessmentrecordchild/export',
    method: 'get',
    params: query
  })
}