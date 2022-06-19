import request from '@/utils/request'

// 查询信号与系统列表
export function listHsag(query) {
  return request({
    url: '/invest/hsag/list',
    method: 'get',
    params: query
  })
}

// 查询信号与系统详细
export function getHsag(sj) {
  return request({
    url: '/invest/hsag/' + sj,
    method: 'get'
  })
}

// 新增信号与系统
export function addHsag(data) {
  return request({
    url: '/invest/hsag',
    method: 'post',
    data: data
  })
}

// 修改信号与系统
export function updateHsag(data) {
  return request({
    url: '/invest/hsag',
    method: 'put',
    data: data
  })
}

// 删除信号与系统
export function delHsag(sj) {
  return request({
    url: '/invest/hsag/' + sj,
    method: 'delete'
  })
}
