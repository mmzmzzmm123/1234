import request from '@/utils/request'

// 查询当前库存存储列表
export function listCurrentinventory(query) {
  return request({
    url: '/data/currentinventory/list',
    method: 'get',
    params: query
  })
}

// 查询当前库存存储详细
export function getCurrentinventory(id) {
  return request({
    url: '/data/currentinventory/' + id,
    method: 'get'
  })
}

// 新增当前库存存储
export function addCurrentinventory(data) {
  return request({
    url: '/data/currentinventory',
    method: 'post',
    data: data
  })
}

// 修改当前库存存储
export function updateCurrentinventory(data) {
  return request({
    url: '/data/currentinventory',
    method: 'put',
    data: data
  })
}

// 删除当前库存存储
export function delCurrentinventory(id) {
  return request({
    url: '/data/currentinventory/' + id,
    method: 'delete'
  })
}

// 导出当前库存存储
export function exportCurrentinventory(query) {
  return request({
    url: '/data/currentinventory/export',
    method: 'get',
    params: query
  })
}