import request from '@/utils/request'

// 查询农场分区列表
export function listFarmzone(query) {
  return request({
    url: '/csa/farmzone/list',
    method: 'get',
    params: query
  })
}

// 查询农场分区详细
export function getFarmzone(code) {
  return request({
    url: '/csa/farmzone/' + code,
    method: 'get'
  })
}

// 新增农场分区
export function addFarmzone(data) {
  return request({
    url: '/csa/farmzone',
    method: 'post',
    data: data
  })
}

// 修改农场分区
export function updateFarmzone(data) {
  return request({
    url: '/csa/farmzone',
    method: 'put',
    data: data
  })
}

// 删除农场分区
export function delFarmzone(code) {
  return request({
    url: '/csa/farmzone/' + code,
    method: 'delete'
  })
}
