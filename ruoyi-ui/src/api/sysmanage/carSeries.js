import request from '@/utils/request'

// 查询车系列表
export function listCarSeries(query) {
  return request({
    url: '/sysmanage/carSeries/list',
    method: 'get',
    params: query
  })
}

// 查询车系详细
export function getCarSeries(id) {
  return request({
    url: '/sysmanage/carSeries/' + id,
    method: 'get'
  })
}

// 新增车系
export function addCarSeries(data) {
  return request({
    url: '/sysmanage/carSeries',
    method: 'post',
    data: data
  })
}

// 修改车系
export function updateCarSeries(data) {
  return request({
    url: '/sysmanage/carSeries',
    method: 'put',
    data: data
  })
}

// 删除车系
export function delCarSeries(id) {
  return request({
    url: '/sysmanage/carSeries/' + id,
    method: 'delete'
  })
}
