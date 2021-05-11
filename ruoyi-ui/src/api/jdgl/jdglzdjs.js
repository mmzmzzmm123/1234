import request from '@/utils/request'

// 查询基地管理制度建设列表
export function listJdglzdjs(query) {
  return request({
    url: '/jdgl/jdglzdjs/list',
    method: 'get',
    params: query
  })
}

// 查询基地管理制度建设详细
export function getJdglzdjs(id) {
  return request({
    url: '/jdgl/jdglzdjs/' + id,
    method: 'get'
  })
}

// 新增基地管理制度建设
export function addJdglzdjs(data) {
  return request({
    url: '/jdgl/jdglzdjs',
    method: 'post',
    data: data
  })
}

// 修改基地管理制度建设
export function updateJdglzdjs(data) {
  return request({
    url: '/jdgl/jdglzdjs',
    method: 'put',
    data: data
  })
}

// 删除基地管理制度建设
export function delJdglzdjs(id) {
  return request({
    url: '/jdgl/jdglzdjs/' + id,
    method: 'delete'
  })
}

// 导出基地管理制度建设
export function exportJdglzdjs(query) {
  return request({
    url: '/jdgl/jdglzdjs/export',
    method: 'get',
    params: query
  })
}