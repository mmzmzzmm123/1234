import request from '@/utils/request'

// 学校评审通过(主持人评审管理-学校评审)
export function passXxpsStatus(data) {
  return request({
    url: '/zcrpsgl/zcrbmsq/passXxpsStatus',
    method: 'put',
    data: data
  })
}

// 学校评审退回(主持人评审管理-学校评审)
export function backXxpsStatus(data) {
  return request({
    url: '/zcrpsgl/zcrbmsq/backXxpsStatus',
    method: 'put',
    data: data
  })
}

