import request from '@/utils/request'

// 查询计划总结（教研员）列表
export function listJyyjhzj(query) {
  return request({
    url: '/jyykhgl/jyyjhzj/list',
    method: 'get',
    params: query
  })
}

// 查询计划总结（教研员）列表
export function listJyyjhzjstatistics(query) {
  return request({
    url: '/jyykhgl/jyyjhzj/liststatistics',
    method: 'get',
    params: query
  })
}

// 查询计划总结（教研员）详细
export function getJyyjhzj(id) {
  return request({
    url: '/jyykhgl/jyyjhzj/' + id,
    method: 'get'
  })
}

// 新增计划总结（教研员）
export function addJyyjhzj(data) {
  return request({
    url: '/jyykhgl/jyyjhzj',
    method: 'post',
    data: data
  })
}

// 修改计划总结（教研员）
export function updateJyyjhzj(data) {
  return request({
    url: '/jyykhgl/jyyjhzj',
    method: 'put',
    data: data
  })
}

// 删除计划总结（教研员）
export function delJyyjhzj(id) {
  return request({
    url: '/jyykhgl/jyyjhzj/' + id,
    method: 'delete'
  })
}

// 导出计划总结（教研员）
export function exportJyyjhzj(query) {
  return request({
    url: '/jyykhgl/jyyjhzj/export',
    method: 'get',
    params: query
  })
}
