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

export function getcurrent() {
  return request({
    url: '/fx_dashboard/current',
    method: 'get'
  })
}

export function DoGetLastGroupReporterData() {
  return request({
    url: '/fx_dashboard/DoGetLastGroupReporterData',
    method: 'get'
  })
}

export function currentBoxAndGroupMonitor() {
  return request({
    url: '/fx_dashboard/currentBoxAndGroupMonitor',
    method: 'get'
  })
}

export function getDateRange(fDateStart,fDateEnd) {


  return request({
    url: '/fx_dashboard/daterange/'+fDateStart+'/'+fDateEnd,
    method: 'get'
  })
}

