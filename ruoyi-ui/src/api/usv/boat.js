import request from '@/utils/request'

// 查询航船列表
export function listBoat(query) {
  return request({
    url: '/usv/boat/list',
    method: 'get',
    params: query
  })
}

// 查询航船详细
export function getBoat(id) {
  return request({
    url: '/usv/boat/' + id,
    method: 'get'
  })
}

// 新增航船
export function addBoat(data) {
  return request({
    url: '/usv/boat',
    method: 'post',
    data: data
  })
}

// 修改航船
export function updateBoat(data) {
  return request({
    url: '/usv/boat',
    method: 'put',
    data: data
  })
}

// 删除航船
export function delBoat(id) {
  return request({
    url: '/usv/boat/' + id,
    method: 'delete'
  })
}

// 导出航船
export function exportBoat(query) {
  return request({
    url: '/usv/boat/export',
    method: 'get',
    params: query
  })
}