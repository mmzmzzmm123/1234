import request from '@/utils/request'

// 查询考核审核过程列表
export function listJzxzkhsh(query) {
  return request({
    url: '/jxzxkhgl/jxzxkhsh/list',
    method: 'get',
    params: query
  })
}

// 查询考核审核过程列表
export function listJzxzkhshByfaid(query) {
  return request({
    url: '/jxzxkhgl/jxzxkhsh/listbyfaid',
    method: 'get',
    params: query
  })
}


// 查询考核审核过程详细
export function getJzxzkhsh(id) {
  return request({
    url: '/jxzxkhgl/jxzxkhsh/' + id,
    method: 'get'
  })
}

// 新增考核审核过程
export function addJzxzkhsh(data) {
  return request({
    url: '/jxzxkhgl/jxzxkhsh',
    method: 'post',
    data: data
  })
}

// 修改考核审核过程
export function updateJzxzkhsh(data) {
  return request({
    url: '/jxzxkhgl/jxzxkhsh',
    method: 'put',
    data: data
  })
}

// 删除考核审核过程
export function delJzxzkhsh(id) {
  return request({
    url: '/jxzxkhgl/jxzxkhsh/' + id,
    method: 'delete'
  })
}

// 导出考核审核过程
export function exportJzxzkhsh(query) {
  return request({
    url: '/jxzxkhgl/jxzxkhsh/export',
    method: 'get',
    params: query
  })
}

// 提交考核审核过程
export function checkJzxzkhsh(id) {
  return request({
    url: '/jxzxkhgl/jxzxkhsh/check/' + id,
    method: 'post'
  })
}

// 删除考核审核过程
export function backJzxzkhsh(id, status) {
  return request({
    url: '/jxzxkhgl/jxzxkhsh/back/' + id + '/' + status,
    method: 'post'
  })
}
