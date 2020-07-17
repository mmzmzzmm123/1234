import request from '@/utils/request'

// 查询服务器详细
export function getDashboardGroup() {
  return request({
    url: '/sd_dashboard/group',
    method: 'get'
  })
}
export function getLastDay() {
  return request({
    url: '/sd_dashboard/lastday',
    method: 'get'
  })
}
export function get30Day() {
  return request({
    url: '/sd_dashboard/30day',
    method: 'get'
  })
}

export function getcurrent() {
  return request({
    url: '/sd_dashboard/current',
    method: 'get'
  })
}

export function getDateRange(fDateStart,fDateEnd) {


  return request({
    url: '/sd_dashboard/daterange/'+fDateStart+'/'+fDateEnd,
    method: 'get'
  })
}

