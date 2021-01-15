import request from '@/utils/request'

// 查询研究活动（教研员）列表
export function listJyyyjhd(query) {
  return request({
    url: '/jyykhgl/jyyyjhd/list',
    method: 'get',
    params: query
  })
}

// 查询研究活动（教研员）详细
export function getJyyyjhd(id) {
  return request({
    url: '/jyykhgl/jyyyjhd/' + id,
    method: 'get'
  })
}

// 新增研究活动（教研员）
export function addJyyyjhd(data) {
  return request({
    url: '/jyykhgl/jyyyjhd',
    method: 'post',
    data: data
  })
}

// 修改研究活动（教研员）
export function updateJyyyjhd(data) {
  return request({
    url: '/jyykhgl/jyyyjhd',
    method: 'put',
    data: data
  })
}

// 删除研究活动（教研员）
export function delJyyyjhd(id) {
  return request({
    url: '/jyykhgl/jyyyjhd/' + id,
    method: 'delete'
  })
}

// 导出研究活动（教研员）
export function exportJyyyjhd(query) {
  return request({
    url: '/jyykhgl/jyyyjhd/export',
    method: 'get',
    params: query
  })
}