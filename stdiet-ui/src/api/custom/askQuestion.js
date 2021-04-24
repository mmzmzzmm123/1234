import request from '@/utils/request'

// 查询营养小知识提问列表
export function listAskQuestion(query) {
  return request({
    url: '/custom/askQuestion/list',
    method: 'get',
    params: query
  })
}

// 查询营养小知识提问详细
export function getAskQuestion(id) {
  return request({
    url: '/custom/askQuestion/' + id,
    method: 'get'
  })
}

// 新增营养小知识提问
export function addAskQuestion(data) {
  return request({
    url: '/custom/askQuestion',
    method: 'post',
    data: data
  })
}

// 修改营养小知识提问
export function updateAskQuestion(data) {
  return request({
    url: '/custom/askQuestion',
    method: 'put',
    data: data
  })
}

// 删除营养小知识提问
export function delAskQuestion(id) {
  return request({
    url: '/custom/askQuestion/' + id,
    method: 'delete'
  })
}

// 导出营养小知识提问
export function exportAskQuestion(query) {
  return request({
    url: '/custom/askQuestion/export',
    method: 'get',
    params: query
  })
}