import request from '@/utils/request'

// 查询服务器详细
export function getDashboardGroup() {
  return request({
    url: '/fx_dashboard/group',
    method: 'get'
  })
}
export function getLastDay() {
  return request({
    url: '/fx_dashboard/lastday',
    method: 'get'
  })
}
export function get30Day() {
  return request({
    url: '/fx_dashboard/30day',
    method: 'get'
  })
}

export function getDateRange(fDateStart,fDateEnd) {


  return request({
    url: '/fx_dashboard/daterange/'+fDateStart+'/'+fDateEnd,
    method: 'get'
  })
}

