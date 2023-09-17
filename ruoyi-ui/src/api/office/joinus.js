import request from '@/utils/request'

// 查询我要加盟列表
export function listJoinus(query) {
  return request({
    url: '/office/joinus/list',
    method: 'get',
    params: query
  })
}

// 查询我要加盟详细
export function getJoinus(id) {
  return request({
    url: '/office/joinus/' + id,
    method: 'get'
  })
}

// 新增我要加盟
export function addJoinus(data) {
  return request({
    url: '/office/joinus',
    method: 'post',
    data: data
  })
}

// 修改我要加盟
export function updateJoinus(data) {
  return request({
    url: '/office/joinus',
    method: 'put',
    data: data
  })
}

// 删除我要加盟
export function delJoinus(id) {
  return request({
    url: '/office/joinus/' + id,
    method: 'delete'
  })
}
