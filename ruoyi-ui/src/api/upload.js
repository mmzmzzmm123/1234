import request from '@/utils/request'

// 登录方法
export function deleteFile(fileName, module) {
  return request({
    url: `/common/upload/delete?fileKey=${fileName}&module=${module}`,
    method: 'post'
  })
}