import request from '@/utils/request'

// 查询平台充值配置列表
export function listPlatformRechargeConfig(query) {
  return request({
    url: '/platform/platformRechargeConfig/list',
    method: 'get',
    params: query
  })
}

// 查询平台充值配置详细
export function getPlatformRechargeConfig(id) {
  return request({
    url: '/platform/platformRechargeConfig/' + id,
    method: 'get'
  })
}

// 新增平台充值配置
export function addPlatformRechargeConfig(data) {
  return request({
    url: '/platform/platformRechargeConfig',
    method: 'post',
    data: data
  })
}

// 修改平台充值配置
export function updatePlatformRechargeConfig(data) {
  return request({
    url: '/platform/platformRechargeConfig',
    method: 'put',
    data: data
  })
}

// 删除平台充值配置
export function delPlatformRechargeConfig(id) {
  return request({
    url: '/platform/platformRechargeConfig/' + id,
    method: 'delete'
  })
}
