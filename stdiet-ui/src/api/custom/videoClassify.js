import request from '@/utils/request'

// 查询视频分类列表
export function listVideoClassify(query) {
  return request({
    url: '/custom/videoClassify/list',
    method: 'get',
    params: query
  })
}

// 查询视频分类详细
export function getVideoClassify(id) {
  return request({
    url: '/custom/videoClassify/' + id,
    method: 'get'
  })
}

// 新增视频分类
export function addVideoClassify(data) {
  return request({
    url: '/custom/videoClassify',
    method: 'post',
    data: data
  })
}

// 修改视频分类
export function updateVideoClassify(data) {
  return request({
    url: '/custom/videoClassify',
    method: 'put',
    data: data
  })
}

// 删除视频分类
export function delVideoClassify(id) {
  return request({
    url: '/custom/videoClassify/' + id,
    method: 'delete'
  })
}

// 导出视频分类
export function exportVideoClassify(query) {
  return request({
    url: '/custom/videoClassify/export',
    method: 'get',
    params: query
  })
}

//获取所有分类
export function getAllClassify(){
  return request({
    url: '/custom/videoClassify/getAllClassify',
    method: 'get'
  })
}