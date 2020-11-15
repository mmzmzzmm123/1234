import request from '@/utils/request'

// 查询评估内容列表
export function listAssessmentcontent(query) {
  return request({
    url: '/benyi/assessmentcontent/list',
    method: 'get',
    params: query
  })
}

// 查询部门下拉树结构
export function treeselect() {
  return request({
    url: '/benyi/assessmentcontent/treeselect',
    method: 'get'
  })
}

// 查询流程下拉树结构
export function treeselectstudy() {
  return request({
    url: '/benyi/assessmentcontent/treeselectstudy',
    method: 'get'
  })
}


// 查询评估内容详细
export function getAssessmentcontent(id) {
  return request({
    url: '/benyi/assessmentcontent/' + id,
    method: 'get'
  })
}


// 查询评估内容详细
export function getAssessmentcontentbyparentid(id) {
  return request({
    url: '/benyi/assessmentcontent/byparentid/' + id,
    method: 'get'
  })
}

// 新增评估内容
export function addAssessmentcontent(data) {
  return request({
    url: '/benyi/assessmentcontent',
    method: 'post',
    data: data
  })
}

// 修改评估内容
export function updateAssessmentcontent(data) {
  return request({
    url: '/benyi/assessmentcontent',
    method: 'put',
    data: data
  })
}

// 删除评估内容
export function delAssessmentcontent(id) {
  return request({
    url: '/benyi/assessmentcontent/' + id,
    method: 'delete'
  })
}

// 导出评估内容
export function exportAssessmentcontent(query) {
  return request({
    url: '/benyi/assessmentcontent/export',
    method: 'get',
    params: query
  })
}

// 查询评估统计
export function getAssessmentStatistics(childid, scope) {
  return request({
    url: '/benyi/assessmentcontent/getassessmentstatistics/' + childid + '/' + scope,
    method: 'get'
  })
}

// 查询领域目标
export function getAssessmentStatisticsmb(childid, scope, type) {
  return request({
    url: '/benyi/assessmentcontent/getassessmentstatisticsmb/' + childid + '/' + scope + '/' + type,
    method: 'get'
  })
}

// 查询评估统计
export function getAssessmentStatisticsbymb(childid, scope, type) {
  return request({
    url: '/benyi/assessmentcontent/getassessmentstatisticsbymb/' + childid + '/' + scope + '/' + type,
    method: 'get'
  })
}
