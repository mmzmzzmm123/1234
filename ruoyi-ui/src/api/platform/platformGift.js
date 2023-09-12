import request from '@/utils/request'

// 查询平台礼物管理列表
export function listPlatformGift(query) {
  return request({
    url: '/platform/platformGift/list',
    method: 'get',
    params: query
  })
}

// 查询平台礼物管理详细
export function getPlatformGift(id) {
  return request({
    url: '/platform/platformGift/' + id,
    method: 'get'
  })
}

// 新增平台礼物管理
export function addPlatformGift(data) {
  return request({
    url: '/platform/platformGift',
    method: 'post',
    data: data
  })
}

// 修改平台礼物管理
export function updatePlatformGift(data) {
  return request({
    url: '/platform/platformGift',
    method: 'put',
    data: data
  })
}

// 删除平台礼物管理
export function delPlatformGift(id) {
  return request({
    url: '/platform/platformGift/' + id,
    method: 'delete'
  })
}
