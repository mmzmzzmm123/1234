import request from '@/utils/request'

// 查询幼儿评估列表
export function listAssessmentchild(query) {
  return request({
    url: '/benyi/assessmentchild/list',
    method: 'get',
    params: query
  })
}

// 查询幼儿评估详细
export function getAssessmentchild(id) {
  return request({
    url: '/benyi/assessmentchild/' + id,
    method: 'get'
  })
}

// 查询幼儿评估字典
export function getAssessmentDictData(id) {
  return request({
    url: '/benyi/assessmentchild/getdicdata/' + id,
    method: 'get'
  })
}

// 新增幼儿评估
export function addAssessmentchild(data) {
  return request({
    url: '/benyi/assessmentchild',
    method: 'post',
    data: data
  })
}

// 修改幼儿评估
export function updateAssessmentchild(data) {
  return request({
    url: '/benyi/assessmentchild',
    method: 'put',
    data: data
  })
}

// 删除幼儿评估
export function delAssessmentchild(id) {
  return request({
    url: '/benyi/assessmentchild/' + id,
    method: 'delete'
  })
}

// 导出幼儿评估
export function exportAssessmentchild(query) {
  return request({
    url: '/benyi/assessmentchild/export',
    method: 'get',
    params: query
  })
}