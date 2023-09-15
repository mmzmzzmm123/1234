import request from '@/utils/request'

// 执行数据转换
export function execTransfer(tabName) {
  return request({
    url: '/tool/transfer/exec/'+tabName,
    method: 'get'
  })
}
