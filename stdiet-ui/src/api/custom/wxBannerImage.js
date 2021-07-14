import request from '@/utils/request'

// 查询小程序banner图列表
export function listWxBannerImage(query) {
  return request({
    url: '/custom/wxBannerImage/list',
    method: 'get',
    params: query
  })
}

// 查询小程序banner图详细
export function getWxBannerImage(id) {
  return request({
    url: '/custom/wxBannerImage/' + id,
    method: 'get'
  })
}

// 新增小程序banner图
export function addWxBannerImage(data) {
  return request({
    url: '/custom/wxBannerImage',
    method: 'post',
    data: data
  })
}

// 修改小程序banner图
export function updateWxBannerImage(data) {
  return request({
    url: '/custom/wxBannerImage',
    method: 'put',
    data: data
  })
}

// 删除小程序banner图
export function delWxBannerImage(id) {
  return request({
    url: '/custom/wxBannerImage/' + id,
    method: 'delete'
  })
}

// 导出小程序banner图
export function exportWxBannerImage(query) {
  return request({
    url: '/custom/wxBannerImage/export',
    method: 'get',
    params: query
  })
}