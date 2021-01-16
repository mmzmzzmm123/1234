import request from '@/utils/request'

// 查询个人研修（教研员）列表
export function listJyygryx(query) {
  return request({
    url: '/jyykhgl/jyygryx/list',
    method: 'get',
    params: query
  })
}

// 查询个人研修（教研员）详细
export function getJyygryx(id) {
  return request({
    url: '/jyykhgl/jyygryx/' + id,
    method: 'get'
  })
}

// 新增个人研修（教研员）
export function addJyygryx(data) {
  return request({
    url: '/jyykhgl/jyygryx',
    method: 'post',
    data: data
  })
}

// 修改个人研修（教研员）
export function updateJyygryx(data) {
  return request({
    url: '/jyykhgl/jyygryx',
    method: 'put',
    data: data
  })
}

// 删除个人研修（教研员）
export function delJyygryx(id) {
  return request({
    url: '/jyykhgl/jyygryx/' + id,
    method: 'delete'
  })
}

// 导出个人研修（教研员）
export function exportJyygryx(query) {
  return request({
    url: '/jyykhgl/jyygryx/export',
    method: 'get',
    params: query
  })
}