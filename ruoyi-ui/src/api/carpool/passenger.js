import request from '@/utils/request'

// 查询乘客信息列表
export function listPassenger(query) {
  return request({
    url: '/carpool/passenger/list',
    method: 'get',
    params: query
  })
}

// 查询乘客信息详细
export function getPassenger(id) {
  return request({
    url: '/carpool/passenger/' + id,
    method: 'get'
  })
}

// 新增乘客信息
export function addPassenger(data) {
  return request({
    url: '/carpool/passenger',
    method: 'post',
    data: data
  })
}

// 修改乘客信息
export function updatePassenger(data) {
  return request({
    url: '/carpool/passenger',
    method: 'put',
    data: data
  })
}

// 删除乘客信息
export function delPassenger(id) {
  return request({
    url: '/carpool/passenger/' + id,
    method: 'delete'
  })
}

// 设置黑名单
export function setBlacklist(data) {
  return request({
    url: '/carpool/blacklist' ,
    method: 'post',
    data: data
  })
}


// 导出乘客信息
export function exportPassenger(query) {
  return request({
    url: '/carpool/passenger/export',
    method: 'get',
    params: query
  })
}
