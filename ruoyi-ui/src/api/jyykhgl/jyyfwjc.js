import request from '@/utils/request'

// 查询服务基层（教研员）列表
export function listJyyfwjc(query) {
  return request({
    url: '/jyykhgl/jyyfwjc/list',
    method: 'get',
    params: query
  })
}

// 查询服务基层（教研员）详细
export function getJyyfwjc(id) {
  return request({
    url: '/jyykhgl/jyyfwjc/' + id,
    method: 'get'
  })
}

// 新增服务基层（教研员）
export function addJyyfwjc(data) {
  return request({
    url: '/jyykhgl/jyyfwjc',
    method: 'post',
    data: data
  })
}

// 修改服务基层（教研员）
export function updateJyyfwjc(data) {
  return request({
    url: '/jyykhgl/jyyfwjc',
    method: 'put',
    data: data
  })
}

// 删除服务基层（教研员）
export function delJyyfwjc(id) {
  return request({
    url: '/jyykhgl/jyyfwjc/' + id,
    method: 'delete'
  })
}

// 导出服务基层（教研员）
export function exportJyyfwjc(query) {
  return request({
    url: '/jyykhgl/jyyfwjc/export',
    method: 'get',
    params: query
  })
}

// 下载用户导入模板
export function importTemplate() {
  return request({
      url: '/jyykhgl/jyyfwjc/importTemplate',
      method: 'get'
  })
}