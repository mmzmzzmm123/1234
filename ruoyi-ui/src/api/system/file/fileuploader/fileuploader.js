import request from '@/utils/request'

// 查询公告列表
export function fileMerge(param) {
  return request({
    url: '/system/file/merge',
    method: 'post',
    params: param
  })
}
