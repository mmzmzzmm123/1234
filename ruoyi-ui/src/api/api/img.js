import request from '@/utils/request'

// 查询车源图片管理列表
export function listImg(query) {
  return request({
    url: '/api/img/list',
    method: 'get',
    params: query
  })
}

// 查询车源图片管理详细
export function getImg(id) {
  return request({
    url: '/api/img/' + id,
    method: 'get'
  })
}

// 新增车源图片管理
export function addImg(data) {
  return request({
    url: '/api/img',
    method: 'post',
    data: data
  })
}

// 修改车源图片管理
export function updateImg(data) {
  return request({
    url: '/api/img',
    method: 'put',
    data: data
  })
}

// 删除车源图片管理
export function delImg(id) {
  return request({
    url: '/api/img/' + id,
    method: 'delete'
  })
}
