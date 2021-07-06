import request from '@/utils/request'

// 查询售前调查，销售给客户发送简易信息调查链接列表
export function listPreSaleSurvey(query) {
  return request({
    url: '/custom/preSaleSurvey/list',
    method: 'get',
    params: query
  })
}

// 查询售前调查，销售给客户发送简易信息调查链接详细
export function getPreSaleSurvey(id) {
  return request({
    url: '/custom/preSaleSurvey/' + id,
    method: 'get'
  })
}

// 新增售前调查，销售给客户发送简易信息调查链接
export function addPreSaleSurvey(data) {
  return request({
    url: '/custom/preSaleSurvey',
    method: 'post',
    data: data
  })
}

// 修改售前调查，销售给客户发送简易信息调查链接
export function updatePreSaleSurvey(data) {
  return request({
    url: '/custom/preSaleSurvey',
    method: 'put',
    data: data
  })
}

// 删除售前调查，销售给客户发送简易信息调查链接
export function delPreSaleSurvey(id) {
  return request({
    url: '/custom/preSaleSurvey/' + id,
    method: 'delete'
  })
}

// 导出售前调查，销售给客户发送简易信息调查链接
export function exportPreSaleSurvey(query) {
  return request({
    url: '/custom/preSaleSurvey/export',
    method: 'get',
    params: query
  })
}