import request from '@/utils/request'

// 查询库存列表
export function listInventory(query) {
  return request({
    url: '/ufreight/inventory/list',
    method: 'get',
    params: query
  })
}

// 查询库存详细
export function getInventory(inventoryId) {
  return request({
    url: '/ufreight/inventory/' + inventoryId,
    method: 'get'
  })
}

// 新增库存
export function addInventory(data) {
  return request({
    url: '/ufreight/inventory',
    method: 'post',
    data: data
  })
}

// 修改库存
export function updateInventory(data) {
  return request({
    url: '/ufreight/inventory',
    method: 'put',
    data: data
  })
}

// 删除库存
export function delInventory(inventoryId) {
  return request({
    url: '/ufreight/inventory/' + inventoryId,
    method: 'delete'
  })
}
