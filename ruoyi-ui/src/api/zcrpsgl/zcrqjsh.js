import request from '@/utils/request'


// 区级评审通过(主持人评审管理-区级评审)
export function passQjpsStatus(data) {
  return request({
    url: '/zcrpsgl/zcrbmsq/passQjpsStatus',
    method: 'put',
    data: data
  })
}

// 区级评审退回(主持人评审管理-区级评审)
export function backQjpsStatus(data) {
  return request({
    url: '/zcrpsgl/zcrbmsq/backQjpsStatus',
    method: 'put',
    data: data
  })
}

