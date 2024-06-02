import request from '@/utils/request'

// 查询行政区域地州市信息列表
export function listCities(query) {
  return request({
    url: '/sysmanage/cities/list',
    method: 'get',
    params: query
  })
}

// 查询行政区域地州市信息详细
export function getCities(id) {
  return request({
    url: '/sysmanage/cities/' + id,
    method: 'get'
  })
}

// 新增行政区域地州市信息
export function addCities(data) {
  return request({
    url: '/sysmanage/cities',
    method: 'post',
    data: data
  })
}

// 修改行政区域地州市信息
export function updateCities(data) {
  return request({
    url: '/sysmanage/cities',
    method: 'put',
    data: data
  })
}

// 删除行政区域地州市信息
export function delCities(id) {
  return request({
    url: '/sysmanage/cities/' + id,
    method: 'delete'
  })
}
