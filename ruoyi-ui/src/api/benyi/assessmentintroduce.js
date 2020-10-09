import request from '@/utils/request'

// 查询评估体系介绍列表
export function listAssessmentintroduce(query) {
  return request({
    url: '/benyi/assessmentintroduce/list',
    method: 'get',
    params: query
  })
}

// 查询评估体系介绍详细
export function getAssessmentintroduce(id) {
  return request({
    url: '/benyi/assessmentintroduce/' + id,
    method: 'get'
  })
}

// 新增评估体系介绍
export function addAssessmentintroduce(data) {
  return request({
    url: '/benyi/assessmentintroduce',
    method: 'post',
    data: data
  })
}

// 修改评估体系介绍
export function updateAssessmentintroduce(data) {
  return request({
    url: '/benyi/assessmentintroduce',
    method: 'put',
    data: data
  })
}

// 删除评估体系介绍
export function delAssessmentintroduce(id) {
  return request({
    url: '/benyi/assessmentintroduce/' + id,
    method: 'delete'
  })
}

// 导出评估体系介绍
export function exportAssessmentintroduce(query) {
  return request({
    url: '/benyi/assessmentintroduce/export',
    method: 'get',
    params: query
  })
}