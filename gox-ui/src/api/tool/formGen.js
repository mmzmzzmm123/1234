import request from '@/utils/request'
export function saveJson(json) {
    return request({
      url: '/tool/gen/form',
      method: 'post',
      params: query
    })
  }