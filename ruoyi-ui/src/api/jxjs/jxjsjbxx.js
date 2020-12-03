import request from '@/utils/request'

// 查询见习教师基本信息列表
export function listJxjsjbxx(query) {
  return request({
    url: '/jxjs/jxjsjbxx/list',
    method: 'get',
    params: query
  })
}

// 查询见习教师基本信息列表
export function listJxjsjbxxnotjdx(query) {
  return request({
    url: '/jxjs/jxjsjbxx/listnotjdx',
    method: 'get',
    params: query
  })
}

// 查询见习教师基本信息列表
export function listJxjsjbxxnotjdcx(query) {
  return request({
    url: '/jxjs/jxjsjbxx/listnotjdcx',
    method: 'get',
    params: query
  })
}

// 查询见习教师基本信息详细
export function getJxjsjbxx(id) {
  return request({
    url: '/jxjs/jxjsjbxx/' + id,
    method: 'get'
  })
}

// 新增见习教师基本信息
export function addJxjsjbxx(data) {
  return request({
    url: '/jxjs/jxjsjbxx',
    method: 'post',
    data: data
  })
}

// 新增基地校
export function updateJxjsJdx(id, jdxid) {
  return request({
    url: '/jxjs/jxjsjbxx/updatejsjdx/' + id + "/" + jdxid,
    method: 'post'
  })
}

// 修改见习教师基本信息
export function updateJxjsjbxx(data) {
  return request({
    url: '/jxjs/jxjsjbxx',
    method: 'put',
    data: data
  })
}

// 删除见习教师基本信息
export function delJxjsjbxx(id) {
  return request({
    url: '/jxjs/jxjsjbxx/' + id,
    method: 'delete'
  })
}

export function clearJxjsjdx(id){
  return request({
    url: '/jxjs/jxjsjbxx/clearjdx/' + id,
    method: 'post'
  })
}

// 导出见习教师基本信息
export function exportJxjsjbxx(query) {
  return request({
    url: '/jxjs/jxjsjbxx/export',
    method: 'get',
    params: query
  })
}
