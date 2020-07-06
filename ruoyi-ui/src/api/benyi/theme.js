import request from '@/utils/request'

// 查询主题整合列表
export function listTheme(query) {
  return request({
    url: '/benyi/theme/list',
    method: 'get',
    params: query
  })
}

// 查询流程下拉树结构
export function treeselect() {
  return request({
      url: '/benyi/theme/treeselect',
      method: 'get'
  })
}

// 查询主题整合详细
export function getTheme(id) {
  return request({
    url: '/benyi/theme/' + id,
    method: 'get'
  })
}

// 新增主题整合
export function addTheme(data) {
  return request({
    url: '/benyi/theme',
    method: 'post',
    data: data
  })
}

// 修改主题整合
export function updateTheme(data) {
  return request({
    url: '/benyi/theme',
    method: 'put',
    data: data
  })
}

// 删除主题整合
export function delTheme(id) {
  return request({
    url: '/benyi/theme/' + id,
    method: 'delete'
  })
}

// 导出主题整合
export function exportTheme(query) {
  return request({
    url: '/benyi/theme/export',
    method: 'get',
    params: query
  })
}