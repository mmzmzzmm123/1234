import request from '@/utils/request'

// 查询通知公告列表
export function listNotice(query) {
  return request({
    url: '/system/notice/list',
    method: 'get',
    params: query
  })
}

// 查询通知公告详细
export function getNotice(noticeId) {
  return request({
    url: '/system/notice/' + noticeId,
    method: 'get'
  })
}

// 新增通知公告
export function addNotice(data) {
  return request({
    url: '/system/notice',
    method: 'post',
    data: data
  })
}

// 修改通知公告
export function updateNotice(data) {
  return request({
    url: '/system/notice',
    method: 'put',
    data: data
  })
}

// 删除通知公告
export function delNotice(noticeId) {
  return request({
    url: '/system/notice/' + noticeId,
    method: 'delete'
  })
}

// 导出通知公告
export function exportNotice(query) {
  return request({
    url: '/system/notice/export',
    method: 'get',
    params: query
  })
}