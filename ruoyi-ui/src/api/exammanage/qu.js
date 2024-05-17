import request from '@/utils/request'

// 查询试题管理列表
export function listQu(query) {
  return request({
    url: '/exammanage/qu/list',
    method: 'get',
    params: query
  })
}

// 查询试题管理详细
export function getQu(id) {
  return request({
    url: '/exammanage/qu/' + id,
    method: 'get'
  })
}

// 新增试题管理
export function addQu(data) {
  return request({
    url: '/exammanage/qu',
    method: 'post',
    data: data
  })
}

// 修改试题管理
export function updateQu(data) {
  return request({
    url: '/exammanage/qu',
    method: 'put',
    data: data
  })
}

// 删除试题管理
export function delQu(id) {
  return request({
    url: '/exammanage/qu/' + id,
    method: 'delete'
  })
}
