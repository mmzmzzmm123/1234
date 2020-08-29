import request from '@/utils/request'

// 查询考核指标项列表
export function listJxzxkhzbx(query) {
  return request({
    url: '/jxzxkhgl/jxzxkhzbx/list',
    method: 'get',
    params: query
  })
}

// 查询考核指标项详细
export function getJxzxkhzbx(id) {
  return request({
    url: '/jxzxkhgl/jxzxkhzbx/' + id,
    method: 'get'
  })
}

// 新增考核指标项
export function addJxzxkhzbx(data) {
  return request({
    url: '/jxzxkhgl/jxzxkhzbx',
    method: 'post',
    data: data
  })
}

// 修改考核指标项
export function updateJxzxkhzbx(data) {
  return request({
    url: '/jxzxkhgl/jxzxkhzbx',
    method: 'put',
    data: data
  })
}

// 删除考核指标项
export function delJxzxkhzbx(id) {
  return request({
    url: '/jxzxkhgl/jxzxkhzbx/' + id,
    method: 'delete'
  })
}

// 导出考核指标项
export function exportJxzxkhzbx(query) {
  return request({
    url: '/jxzxkhgl/jxzxkhzbx/export',
    method: 'get',
    params: query
  })
}
