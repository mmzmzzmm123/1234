import request from '@/utils/request'

// 查询充电桩列表
export function listPile(query) {
  return request({
    url: '/usv/pile/list',
    method: 'get',
    params: query
  })
}

// 查询充电桩详细
export function getPile(chargingPileId) {
  return request({
    url: '/usv/pile/' + chargingPileId,
    method: 'get'
  })
}

// 新增充电桩
export function addPile(data) {
  return request({
    url: '/usv/pile',
    method: 'post',
    data: data
  })
}

// 修改充电桩
export function updatePile(data) {
  return request({
    url: '/usv/pile',
    method: 'put',
    data: data
  })
}

// 删除充电桩
export function delPile(chargingPileId) {
  return request({
    url: '/usv/pile/' + chargingPileId,
    method: 'delete'
  })
}

// 导出充电桩
export function exportPile(query) {
  return request({
    url: '/usv/pile/export',
    method: 'get',
    params: query
  })
}