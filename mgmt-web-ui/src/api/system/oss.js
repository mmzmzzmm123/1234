import request from '@/utils/request'

// 查询文件上传列表
export function listOss(query) {
  return request({
    url: '/system/oss/list',
    method: 'get',
    params: query
  })
}

// 查询文件上传详细
export function getOss(id) {
  return request({
    url: '/system/oss/' + id,
    method: 'get'
  })
}

// 新增文件上传
export function addOss(data) {
  return request({
    url: '/system/oss',
    method: 'post',
    data: data
  })
}

// 修改文件上传
export function updateOss(data) {
  return request({
    url: '/system/oss',
    method: 'put',
    data: data
  })
}

// 删除文件上传
export function delOss(id) {
  return request({
    url: '/system/oss/' + id,
    method: 'delete'
  })
}
