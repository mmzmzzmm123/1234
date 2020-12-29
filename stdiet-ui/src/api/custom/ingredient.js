import request from '@/utils/request'

// 查询食材列表
export function listIngredient(query) {
  const {recIds, notRecIds} = query;
  return request({
    url: '/custom/ingredient/list',
    method: 'post',
    data: {
      ...query,
      recIds: recIds && recIds.length ? recIds : null,
      notRecIds: notRecIds && notRecIds.length ? notRecIds : null,
    }
  })
}

// 查询食材详细
export function getIngredient(id) {
  return request({
    url: '/custom/ingredient/' + id,
    method: 'get'
  })
}

// 新增食材
export function addIngredient(data) {
  return request({
    url: '/custom/ingredient',
    method: 'post',
    data: data
  })
}

// 修改食材
export function updateIngredient(data) {
  return request({
    url: '/custom/ingredient',
    method: 'put',
    data: data
  })
}

// 删除食材
export function delIngredient(id) {
  return request({
    url: '/custom/ingredient/' + id,
    method: 'delete'
  })
}

// 导出食材
export function exportIngredient(query) {
  return request({
    url: '/custom/ingredient/export',
    method: 'get',
    params: query
  })
}
