import request from '@/utils/request'

// 查询农场会员列表
export function listFarmer(query) {
  return request({
    url: '/csa/farmer/list',
    method: 'get',
    params: query
  })
}

// 查询农场会员详细
export function getFarmer(farmerId) {
  return request({
    url: '/csa/farmer/' + farmerId,
    method: 'get'
  })
}

// 新增农场会员
export function addFarmer(data) {
  return request({
    url: '/csa/farmer',
    method: 'post',
    data: data
  })
}

// 修改农场会员
export function updateFarmer(data) {
  return request({
    url: '/csa/farmer',
    method: 'put',
    data: data
  })
}

// 删除农场会员
export function delFarmer(farmerId) {
  return request({
    url: '/csa/farmer/' + farmerId,
    method: 'delete'
  })
}
