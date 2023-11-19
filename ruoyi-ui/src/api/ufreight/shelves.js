import request from '@/utils/request'

// 查询仓库货架列表
export function listShelves(query) {
  return request({
    url: '/ufreight/shelves/list',
    method: 'get',
    params: query
  })
}

// 查询仓库货架详细
export function getShelves(shelfId) {
  return request({
    url: '/ufreight/shelves/' + shelfId,
    method: 'get'
  })
}

// 新增仓库货架
export function addShelves(data) {
  return request({
    url: '/ufreight/shelves',
    method: 'post',
    data: data
  })
}

// 修改仓库货架
export function updateShelves(data) {
  return request({
    url: '/ufreight/shelves',
    method: 'put',
    data: data
  })
}

// 删除仓库货架
export function delShelves(shelfId) {
  return request({
    url: '/ufreight/shelves/' + shelfId,
    method: 'delete'
  })
}
