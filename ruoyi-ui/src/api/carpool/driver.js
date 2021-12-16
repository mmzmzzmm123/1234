import request from '@/utils/request'

// 查询司机信息列表
export function listDriver(query) {
  return request({
    url: '/carpool/driver/list',
    method: 'get',
    params: query
  })
}

// 查询司机信息详细
export function getDriver(id) {
  return request({
    url: '/carpool/driver/' + id,
    method: 'get'
  })
}

// 新增司机信息
export function addDriver(data) {
  return request({
    url: '/carpool/driver',
    method: 'post',
    data: data
  })
}

// 修改司机信息
export function updateDriver(data) {
  return request({
    url: '/carpool/driver',
    method: 'put',
    data: data
  })
}

// 删除司机信息
export function delDriver(id) {
  return request({
    url: '/carpool/driver/' + id,
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



// 导出司机信息
export function exportDriver(query) {
  return request({
    url: '/carpool/driver/export',
    method: 'get',
    params: query
  })
}
