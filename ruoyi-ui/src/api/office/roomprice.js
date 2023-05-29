import request from '@/utils/request'

// 查询房间价格列表
export function listRoomprice(query) {
  return request({
    url: '/office/roomprice/list',
    method: 'get',
    params: query
  })
}

// 查询房间价格详细
export function getRoomprice(id) {
  return request({
    url: '/office/roomprice/' + id,
    method: 'get'
  })
}

// 新增房间价格
export function addRoomprice(data) {
  return request({
    url: '/office/roomprice',
    method: 'post',
    data: data
  })
}

// 修改房间价格
export function updateRoomprice(data) {
  return request({
    url: '/office/roomprice',
    method: 'put',
    data: data
  })
}

// 删除房间价格
export function delRoomprice(id) {
  return request({
    url: '/office/roomprice/' + id,
    method: 'delete'
  })
}
