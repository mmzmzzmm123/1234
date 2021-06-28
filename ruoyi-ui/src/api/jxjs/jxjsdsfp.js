import request from '@/utils/request'

// 查询见习教师导师分配列表
export function listJxjsdsfp(query) {
  return request({
    url: '/jxjs/jxjsdsfp/list',
    method: 'get',
    params: query
  })
}

// 查询见习教师导师分配详细
export function getJxjsdsfp(id) {
  return request({
    url: '/jxjs/jxjsdsfp/' + id,
    method: 'get'
  })
}

// 新增见习教师导师分配
export function addJxjsdsfp(data) {
  return request({
    url: '/jxjs/jxjsdsfp',
    method: 'post',
    data: data
  })
}

// 修改见习教师导师分配
export function updateJxjsdsfp(data) {
  return request({
    url: '/jxjs/jxjsdsfp',
    method: 'put',
    data: data
  })
}

// 删除见习教师导师分配
export function delJxjsdsfp(id) {
  return request({
    url: '/jxjs/jxjsdsfp/' + id,
    method: 'delete'
  })
}

// 导出见习教师导师分配
export function exportJxjsdsfp(query) {
  return request({
    url: '/jxjs/jxjsdsfp/export',
    method: 'get',
    params: query
  })
}
