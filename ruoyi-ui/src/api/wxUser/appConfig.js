import request from '@/utils/request'

// 查询微信小程序配置列表
export function listAppConfig(query) {
  return request({
    url: '/wxUser/appConfig/list',
    method: 'get',
    params: query
  })
}

// 查询微信小程序配置详细
export function getAppConfig(id) {
  return request({
    url: '/wxUser/appConfig/' + id,
    method: 'get'
  })
}

// 新增微信小程序配置
export function addAppConfig(data) {
  return request({
    url: '/wxUser/appConfig',
    method: 'post',
    data: data
  })
}

// 修改微信小程序配置
export function updateAppConfig(data) {
  return request({
    url: '/wxUser/appConfig',
    method: 'put',
    data: data
  })
}

// 删除微信小程序配置
export function delAppConfig(id) {
  return request({
    url: '/wxUser/appConfig/' + id,
    method: 'delete'
  })
}
