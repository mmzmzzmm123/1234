import request from '@/utils/request'

// 查询学校信息列表
export function listXxjbxx(query) {
  return request({
    url: '/qtjs/xxjbxx/list',
    method: 'get',
    params: query
  })
}

// 查询学校信息详细
export function getXxjbxx(id) {
  return request({
    url: '/qtjs/xxjbxx/' + id,
    method: 'get'
  })
}

// 新增学校信息
export function addXxjbxx(data) {
  return request({
    url: '/qtjs/xxjbxx',
    method: 'post',
    data: data
  })
}

// 修改学校信息
export function updateXxjbxx(data) {
  return request({
    url: '/qtjs/xxjbxx',
    method: 'put',
    data: data
  })
}

// 删除学校信息
export function delXxjbxx(id) {
  return request({
    url: '/qtjs/xxjbxx/' + id,
    method: 'delete'
  })
}

// 导出学校信息
export function exportXxjbxx(query) {
  return request({
    url: '/qtjs/xxjbxx/export',
    method: 'get',
    params: query
  })
}