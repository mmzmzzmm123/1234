import request from '@/utils/request'

// 查询服务器详细
export function getDensity30Day() {
  return request({
    url: '/sd_density/30day',
    method: 'get'
  })
}
export function getDensity72h() {
  return request({
    url: '/sd_density/72h',
    method: 'get'
  })
}
export function getDensityGroup() {
  return request({
    url: '/sd_density/group',
    method: 'get'
  })
}


export function getDensityDataRange(fDateStart,fDateEnd) {
  return request({
    url: '/sd_density/getDensityDataRange/'+fDateStart+'/'+fDateEnd,
    method: 'get'
  })
}
