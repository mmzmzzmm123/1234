import request from '@/utils/request'

// 查询主任信息列表
export function listHeadmaster(query) {
  return request({
    url: '/headmaster/headmaster/list',
    method: 'get',
    params: query
  })
}

// 查询主任信息详细
export function getHeadmaster(id) {
  return request({
    url: '/headmaster/headmaster/' + id,
    method: 'get'
  })
}

// 新增主任信息
export function addHeadmaster(data) {
  return request({
    url: '/headmaster/headmaster',
    method: 'post',
    data: data
  })
}

// 修改主任信息
export function updateHeadmaster(data) {
  return request({
    url: '/headmaster/headmaster',
    method: 'put',
    data: data
  })
}

// 删除主任信息
export function delHeadmaster(id) {
  return request({
    url: '/headmaster/headmaster/' + id,
    method: 'delete'
  })
}
