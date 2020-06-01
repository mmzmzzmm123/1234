import request from '@/utils/request'

// 查询培训视频评价反馈列表
export function listFeedback(query) {
  return request({
    url: '/benyi/feedback/list',
    method: 'get',
    params: query
  })
}

// 查询培训视频评价反馈详细
export function getFeedback(id) {
  return request({
    url: '/benyi/feedback/' + id,
    method: 'get'
  })
}

// // 查询培训视频评价反馈详细
// export function getFeedbackByVideo(vid) {
//   return request({
//     url: '/benyi/feedback/video/' + vid,
//     method: 'get'
//   })
// }

// 新增培训视频评价反馈
export function addFeedback(data) {
  return request({
    url: '/benyi/feedback',
    method: 'post',
    data: data
  })
}

// 修改培训视频评价反馈
export function updateFeedback(data) {
  return request({
    url: '/benyi/feedback',
    method: 'put',
    data: data
  })
}

// 删除培训视频评价反馈
export function delFeedback(id) {
  return request({
    url: '/benyi/feedback/' + id,
    method: 'delete'
  })
}

// 导出培训视频评价反馈
export function exportFeedback(query) {
  return request({
    url: '/benyi/feedback/export',
    method: 'get',
    params: query
  })
}