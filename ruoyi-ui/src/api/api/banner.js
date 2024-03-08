import request from '@/utils/request'

// 查询banner管理列表
export function listBanner(query) {
  return request({
    url: '/api/banner/list',
    method: 'get',
    params: query
  })
}

// 查询banner管理详细
export function getBanner(id) {
  return request({
    url: '/api/banner/' + id,
    method: 'get'
  })
}

// 新增banner管理
export function addBanner(data) {
  return request({
    url: '/api/banner',
    method: 'post',
    data: data
  })
}

// 修改banner管理
export function updateBanner(data) {
  return request({
    url: '/api/banner',
    method: 'put',
    data: data
  })
}

// 删除banner管理
export function delBanner(id) {
  return request({
    url: '/api/banner/' + id,
    method: 'delete'
  })
}
