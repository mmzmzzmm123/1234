import request from '@/utils/request'

// 查询房间打扫记录列表
export function listCleanrecord(query) {
  return request({
    url: '/office/cleanrecord/list',
    method: 'get',
    params: query
  })
}

// 查询房间打扫记录详细
export function getCleanrecord(id) {
  return request({
    url: '/office/cleanrecord/' + id,
    method: 'get'
  })
}

// 新增房间打扫记录
export function addCleanrecord(data) {
  return request({
    url: '/office/cleanrecord',
    method: 'post',
    data: data
  })
}

// 修改房间打扫记录
export function updateCleanrecord(data) {
  return request({
    url: '/office/cleanrecord',
    method: 'put',
    data: data
  })
}

// 删除房间打扫记录
export function delCleanrecord(id) {
  return request({
    url: '/office/cleanrecord/' + id,
    method: 'delete'
  })
}
