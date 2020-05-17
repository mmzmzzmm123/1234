import request from '@/utils/request'

// 查询数据模型列表
export function listForm(query) {
  return request({
    url: '/system/form/list',
    method: 'get',
    params: query
  })
}

// 查询数据模型详细
export function getForm(formId) {
  return request({
    url: '/system/form/' + formId,
    method: 'get'
  })
}

// 新增数据模型
export function addForm(data) {
  return request({
    url: '/system/form',
    method: 'post',
    data: data
  })
}

// 修改数据模型
export function updateForm(data) {
  return request({
    url: '/system/form',
    method: 'put',
    data: data
  })
}

// 删除数据模型
export function delForm(formId) {
  return request({
    url: '/system/form/' + formId,
    method: 'delete'
  })
}

// 导出数据模型
export function exportForm(query) {
  return request({
    url: '/system/form/export',
    method: 'get',
    params: query
  })
}