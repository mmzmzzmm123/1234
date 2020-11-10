import request from '@/utils/request'

// 查询见习导师分配列表
export function listJxzxdsfp(query) {
  return request({
    url: '/jxzxkhgl/jxzxdsfp/list',
    method: 'get',
    params: query
  })
}

// 查询见习导师分配详细
export function getJxzxdsfp(id) {
  return request({
    url: '/jxzxkhgl/jxzxdsfp/' + id,
    method: 'get'
  })
}

// 新增见习导师分配
export function addJxzxdsfp(data) {
  return request({
    url: '/jxzxkhgl/jxzxdsfp',
    method: 'post',
    data: data
  })
}

// 修改见习导师分配
export function updateJxzxdsfp(data) {
  return request({
    url: '/jxzxkhgl/jxzxdsfp',
    method: 'put',
    data: data
  })
}

// 删除见习导师分配
export function delJxzxdsfp(id) {
  return request({
    url: '/jxzxkhgl/jxzxdsfp/' + id,
    method: 'delete'
  })
}

// 导出见习导师分配
export function exportJxzxdsfp(query) {
  return request({
    url: '/jxzxkhgl/jxzxdsfp/export',
    method: 'get',
    params: query
  })
}