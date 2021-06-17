import request from '@/utils/request'

// 查询营养视频列表
export function listNutritionalVideo(query) {
  return request({
    url: '/custom/nutritionalVideo/list',
    method: 'get',
    params: query
  })
}

// 查询营养视频详细
export function getNutritionalVideo(id) {
  return request({
    url: '/custom/nutritionalVideo/' + id,
    method: 'get'
  })
}

// 新增营养视频
export function addNutritionalVideo(data) {
  return request({
    url: '/custom/nutritionalVideo',
    method: 'post',
    data: data
  })
}

// 修改营养视频
export function updateNutritionalVideo(data) {
  return request({
    url: '/custom/nutritionalVideo',
    method: 'put',
    data: data
  })
}

// 删除营养视频
export function delNutritionalVideo(id) {
  return request({
    url: '/custom/nutritionalVideo/' + id,
    method: 'delete'
  })
}

// 导出营养视频
export function exportNutritionalVideo(query) {
  return request({
    url: '/custom/nutritionalVideo/export',
    method: 'get',
    params: query
  })
}

// 获取上传凭证
export function getUploadVideoAuth(data) {
  return request({
    url: '/custom/nutritionalVideo/getUploadVideoAuth',
    method: 'post',
    data: data
  })
}

// 修改视频展示状态
export function updateWxShow(data){
  return request({
    url: '/custom/nutritionalVideo/updateWxShow',
    method: 'get',
    params: data
  })
}

// 查询营养视频播放地址
export function getVideoPlayUrlById(id) {
  return request({
    url: '/custom/nutritionalVideo/getVideoPlayUrlById/' + id,
    method: 'get'
  })
}

// 根据视频videoId提交视频截图请求
export function submitVideoSnapshot(id) {
  return request({
    url: '/custom/nutritionalVideo/submitVideoSnapshot',
    method: 'get',
    params: {'videoId': id}
  })
}

//根据视频videoId获取视频截图
export function getVideoSnapshot(id) {
  return request({
    url: '/custom/nutritionalVideo/getVideoSnapshot',
    method: 'get',
    params: {'videoId': id}
  })
}



