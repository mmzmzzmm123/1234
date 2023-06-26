import request from '@/utils/request'

// 查询咨询banner配置列表
export function listPsyBannerConfig(query) {
  return request({
    url: '/psychology/psyBannerConfig/list',
    method: 'get',
    params: query
  })
}

// 查询咨询banner配置详细
export function getPsyBannerConfig(id) {
  return request({
    url: '/psychology/psyBannerConfig/' + id,
    method: 'get'
  })
}

// 新增咨询banner配置
export function addPsyBannerConfig(data) {
  return request({
    url: '/psychology/psyBannerConfig',
    method: 'post',
    data: data
  })
}

// 修改咨询banner配置
export function updatePsyBannerConfig(data) {
  return request({
    url: '/psychology/psyBannerConfig',
    method: 'put',
    data: data
  })
}

// 删除咨询banner配置
export function delPsyBannerConfig(ids) {
  return request({
    url: '/psychology/psyBannerConfig/' + ids,
    method: 'delete',
    data: ids
  })
}
