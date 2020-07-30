import request from '@/utils/request'

// 查询入园半日体验计划列表
export function listHalfdayplan(query) {
  return request({
    url: '/benyi/halfdayplan/list',
    method: 'get',
    params: query
  })
}

// 查询入园半日体验计划详细
export function getHalfdayplan(id) {
  return request({
    url: '/benyi/halfdayplan/' + id,
    method: 'get'
  })
}

// 查询入班体验申请详细
export function getHalfdayplan_query(id) {
  return request({
    url: '/benyi/halfdayplan/getInfo/'+id,
    method: 'get'
  })
}

// 新增入园半日体验计划
export function addHalfdayplan(data) {
  return request({
    url: '/benyi/halfdayplan',
    method: 'post',
    data: data
  })
}

// 修改入园半日体验计划
export function updateHalfdayplan(data) {
  return request({
    url: '/benyi/halfdayplan',
    method: 'put',
    data: data
  })
}

// 删除入园半日体验计划
export function delHalfdayplan(id) {
  return request({
    url: '/benyi/halfdayplan/' + id,
    method: 'delete'
  })
}

// 复制入园通知书
export function copyHalfdayplan(id) {
  return request({
    url: '/benyi/halfdayplan/copy/' + id,
    method: 'post'
  })
}

// 导出入园半日体验计划
export function exportHalfdayplan(query) {
  return request({
    url: '/benyi/halfdayplan/export',
    method: 'get',
    params: query
  })
}
