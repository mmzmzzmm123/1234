import request from '@/utils/request'

// 查询首页图片配置列表
export function listIndexImg(query) {
  return request({
    url: '/system/indexImg/list',
    method: 'get',
    params: query
  })
}

// 查询首页图片配置详细
export function getIndexImg(id) {
  return request({
    url: '/system/indexImg/' + id,
    method: 'get'
  })
}

// 新增首页图片配置
export function addIndexImg(data) {
  return request({
    url: '/system/indexImg',
    method: 'post',
    data: data
  })
}

// 修改首页图片配置
export function updateIndexImg(data) {
  return request({
    url: '/system/indexImg',
    method: 'put',
    data: data
  })
}

// 删除首页图片配置
export function delIndexImg(id) {
  return request({
    url: '/system/indexImg/' + id,
    method: 'delete'
  })
}
