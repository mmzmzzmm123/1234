import request from '@/utils/request'

// 查询链接仓库列表
export function listRepo(query) {
  return request({
    url: '/system/repo/list',
    method: 'get',
    params: query
  })
}

// 查询链接仓库详细
export function getRepo(id) {
  return request({
    url: '/system/repo/' + id,
    method: 'get'
  })
}

// 新增链接仓库
export function addRepo(data) {
  return request({
    url: '/system/repo',
    method: 'post',
    data: data
  })
}

// 修改链接仓库
export function updateRepo(data) {
  return request({
    url: '/system/repo',
    method: 'put',
    data: data
  })
}

// 删除链接仓库
export function delRepo(id) {
  return request({
    url: '/system/repo/' + id,
    method: 'delete'
  })
}

// 导出链接仓库
export function exportRepo(query) {
  return request({
    url: '/system/repo/export',
    method: 'get',
    params: query
  })
}
// 获取大屏id
export function getVisualId() {
  return request({
    url: '/system/repo/visualIds',
    method: 'get'
  })
}
// 获取全景id
export function getPanIds() {
  return request({
    url: '/system/repo/panoramaIds',
    method: 'get'
  })
}
// 获取用户id
export function getUsers() {
  return request({
    url: '/system/repo/users',
    method: 'get'
  })
}

//检查用户是否过期
export function checkExpire(id,panoramaId) {
  return request({
    url: '/system/repo/expireCheck?visualId='+id+'&panId='+panoramaId,
    method: 'get'
  })
}
