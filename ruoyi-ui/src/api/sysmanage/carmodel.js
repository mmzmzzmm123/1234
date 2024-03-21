import request from '@/utils/request'

// 查询汽车型号列表
export function listCarModel(query) {
  return request({
    url: '/sysmanage/carModel/list',
    method: 'get',
    params: query
  })
}

// 查询汽车型号详细
export function getCarModel(id) {
  return request({
    url: '/sysmanage/carModel/' + id,
    method: 'get'
  })
}

// 新增汽车型号
export function addCarModel(data) {
  return request({
    url: '/sysmanage/carModel',
    method: 'post',
    data: data
  })
}

// 修改汽车型号
export function updateCarModel(data) {
  return request({
    url: '/sysmanage/carModel',
    method: 'put',
    data: data
  })
}

// 删除汽车型号
export function delCarModel(id) {
  return request({
    url: '/sysmanage/carModel/' + id,
    method: 'delete'
  })
}
