import request from '@/utils/request'

// 查询公告列表
export function listApplyNotice(query) {
  return request({
    url: '/system/projectNotice/list',
    method: 'get',
    params: query
  })
}

// 查询公告详细
export function getApplyNotice(noticeId) {
  return request({
    url: '/system/projectNotice/' + noticeId,
    method: 'get'
  })
}

// 新增公告
export function addApplyNotice(data) {
  return request({
    url: '/system/projectNotice',
    method: 'post',
    data: data
  })
}

// 修改公告
export function updateApplyNotice(data) {
  return request({
    url: '/system/projectNotice',
    method: 'put',
    data: data
  })
}

// 删除公告
export function delApplyNotice(noticeId) {
  return request({
    url: '/system/projectNotice/' + noticeId,
    method: 'delete'
  })
}