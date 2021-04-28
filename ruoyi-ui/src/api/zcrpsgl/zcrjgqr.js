import request from '@/utils/request'


// 结果确认通过(主持人评审管理-结果确认)
export function passJgqrStatus(data) {
  return request({
    url: '/zcrpsgl/zcrbmsq/passJgqrStatus',
    method: 'put',
    data: data
  })
}

// 结果确认退回(主持人评审管理-结果确认)
export function backJgqrStatus(data) {
  return request({
    url: '/zcrpsgl/zcrbmsq/backJgqrStatus',
    method: 'put',
    data: data
  })
}

