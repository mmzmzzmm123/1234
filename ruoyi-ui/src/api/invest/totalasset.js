import request from '@/utils/request'

// 查询总资产走势列表
export function listTotalasset(query) {
  return request({
    url: '/invest/totalasset/list',
    method: 'get',
    params: query
  })
}

// 查询总资产走势详细
export function getTotalasset(riqi) {
  return request({
    url: '/invest/totalasset/' + riqi,
    method: 'get'
  })
}

// 新增总资产走势
export function addTotalasset(data) {
  return request({
    url: '/invest/totalasset',
    method: 'post',
    data: data
  })
}

// 修改总资产走势
export function updateTotalasset(data) {
  return request({
    url: '/invest/totalasset',
    method: 'put',
    data: data
  })
}

// 删除总资产走势
export function delTotalasset(riqi) {
  return request({
    url: '/invest/totalasset/' + riqi,
    method: 'delete'
  })
}
