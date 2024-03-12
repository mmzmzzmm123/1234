import request from '@/utils/request'

// 查询汽车型号列表
export function listCarmodel(query) {
  return request({
    url: '/sysmanage/carmodel/list',
    method: 'get',
    params: query
  })
}

// 查询汽车型号详细
export function getCarmodel(id) {
  return request({
    url: '/sysmanage/carmodel/' + id,
    method: 'get'
  })
}

// 新增汽车型号
export function addCarmodel(data) {
  return request({
    url: '/sysmanage/carmodel',
    method: 'post',
    data: data
  })
}

// 修改汽车型号
export function updateCarmodel(data) {
  return request({
    url: '/sysmanage/carmodel',
    method: 'put',
    data: data
  })
}

// 删除汽车型号
export function delCarmodel(id) {
  return request({
    url: '/sysmanage/carmodel/' + id,
    method: 'delete'
  })
}
