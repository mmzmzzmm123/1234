import request from '@/utils/request'

// 查询夜间费用配置列表
export function listPlatformNightFeeConfigure(query) {
  return request({
    url: '/platform/platformNightFeeConfigure/list',
    method: 'get',
    params: query
  })
}

// 查询夜间费用配置详细
export function getPlatformNightFeeConfigure(id) {
  return request({
    url: '/platform/platformNightFeeConfigure/' + id,
    method: 'get'
  })
}

// 新增夜间费用配置
export function addPlatformNightFeeConfigure(data) {
  return request({
    url: '/platform/platformNightFeeConfigure',
    method: 'post',
    data: data
  })
}

// 修改夜间费用配置
export function updatePlatformNightFeeConfigure(data) {
  return request({
    url: '/platform/platformNightFeeConfigure',
    method: 'put',
    data: data
  })
}

// 删除夜间费用配置
export function delPlatformNightFeeConfigure(id) {
  return request({
    url: '/platform/platformNightFeeConfigure/' + id,
    method: 'delete'
  })
}
