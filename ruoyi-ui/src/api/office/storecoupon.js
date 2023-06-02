import request from '@/utils/request'

// 查询卡券列表
export function listStorecoupon(query) {
  return request({
    url: '/office/storecoupon/list',
    method: 'get',
    params: query
  })
}

// 查询卡券详细
export function getStorecoupon(id) {
  return request({
    url: '/office/storecoupon/' + id,
    method: 'get'
  })
}

// 新增卡券
export function addStorecoupon(data) {
  return request({
    url: '/office/storecoupon',
    method: 'post',
    data: data
  })
}

// 修改卡券
export function updateStorecoupon(data) {
  return request({
    url: '/office/storecoupon',
    method: 'put',
    data: data
  })
}

// 删除卡券
export function delStorecoupon(id) {
  return request({
    url: '/office/storecoupon/' + id,
    method: 'delete'
  })
}
