import request from '@/utils/request'

// 查询平台广告图列表
export function listPlatformBanner(query) {
  return request({
    url: '/platform/platformBanner/list',
    method: 'get',
    params: query
  })
}

// 查询平台广告图详细
export function getPlatformBanner(id) {
  return request({
    url: '/platform/platformBanner/' + id,
    method: 'get'
  })
}

// 新增平台广告图
export function addPlatformBanner(data) {
  return request({
    url: '/platform/platformBanner',
    method: 'post',
    data: data
  })
}

// 修改平台广告图
export function updatePlatformBanner(data) {
  return request({
    url: '/platform/platformBanner',
    method: 'put',
    data: data
  })
}

// 删除平台广告图
export function delPlatformBanner(id) {
  return request({
    url: '/platform/platformBanner/' + id,
    method: 'delete'
  })
}
