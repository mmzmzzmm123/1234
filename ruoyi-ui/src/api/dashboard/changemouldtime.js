import request from '@/utils/request'

// 查询服务器详细
export function getChangeMouldTimeList() {
  return request({
    url: '/fx_changemouldtime/list',
    method: 'get'
  })
}


export function getDateRangeChangeMouldTime(fDateStart, fDateEnd) {
  return request({
    url: '/fx_changemouldtime/rangetimeavg/' + fDateStart + '/' + fDateEnd,
    method: 'get'
  })
}


export function DoGetChangeMouldTimeListDateRange(fDateStart, fDateEnd) {
  return request({
    url: '/fx_changemouldtime/DoGetChangeMouldTimeListDateRange/' + fDateStart + '/' + fDateEnd,
    method: 'get'
  })
}
