import request from '@/utils/request'

// 查询私家农场会员列表
export function listFarmer(query) {
  return request({
    url: '/csa/farmer/list',
    method: 'get',
    params: query
  })
}

// 查询私家农场会员详细
export function getFarmer(id) {
  return request({
    url: '/csa/farmer/' + id,
    method: 'get'
  })
}

// 新增私家农场会员
export function addFarmer(data) {
  return request({
    url: '/csa/farmer',
    method: 'post',
    data: data
  })
}

// 修改私家农场会员
export function updateFarmer(data) {
  return request({
    url: '/csa/farmer',
    method: 'put',
    data: data
  })
}

// 删除私家农场会员
export function delFarmer(id) {
  return request({
    url: '/csa/farmer/' + id,
    method: 'delete'
  })
}
