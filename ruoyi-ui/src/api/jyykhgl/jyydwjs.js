import request from '@/utils/request'

// 查询队伍建设（教研员）列表
export function listJyydwjs(query) {
  return request({
    url: '/jyykhgl/jyydwjs/list',
    method: 'get',
    params: query
  })
}

// 查询队伍建设（教研员）详细
export function getJyydwjs(id) {
  return request({
    url: '/jyykhgl/jyydwjs/' + id,
    method: 'get'
  })
}

// 新增队伍建设（教研员）
export function addJyydwjs(data) {
  return request({
    url: '/jyykhgl/jyydwjs',
    method: 'post',
    data: data
  })
}

// 修改队伍建设（教研员）
export function updateJyydwjs(data) {
  return request({
    url: '/jyykhgl/jyydwjs',
    method: 'put',
    data: data
  })
}

// 删除队伍建设（教研员）
export function delJyydwjs(id) {
  return request({
    url: '/jyykhgl/jyydwjs/' + id,
    method: 'delete'
  })
}

// 导出队伍建设（教研员）
export function exportJyydwjs(query) {
  return request({
    url: '/jyykhgl/jyydwjs/export',
    method: 'get',
    params: query
  })
}