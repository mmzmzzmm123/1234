import request from '@/utils/request'

// 登录方法
export function deleteFile(fileName, module) {
  return request({
    url: `/common/upload/delete?fileKey=${fileName}&module=${module}`,
    method: 'post'
  })
}

// 上传图片
export function uploadFile(data, module, type) {
  return request({
    url: '/common/upload',
    method: 'post',
    headers: {
      'module': module,
      'type': type,
    },
    data: data
  })
}
