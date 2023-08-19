import request from '@/utils/request'

// 合并文件
export function fileMerge(param) {
  return request({
    url: '/system/file/merge',
    method: 'post',
    params: param
  })
}
