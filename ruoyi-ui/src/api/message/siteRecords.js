import request from '@/utils/request'

// 查询站内信消息列表
export function listSiteRecords(query) {
  return request({
    url: '/message/siteRecords/list',
    method: 'get',
    params: query
  })
}

// 查询站内信消息详细
export function getSiteRecords(msgSiteId) {
  return request({
    url: '/message/siteRecords/' + msgSiteId,
    method: 'get'
  })
}

// 新增站内信消息
export function addSiteRecords(data) {
  return request({
    url: '/message/siteRecords',
    method: 'post',
    data: data
  })
}

// 发送站内信消息（临时全体消息）
export function sendAllSiteRecords(data) {
  return request({
    url: '/message/siteRecords/sendAll',
    method: 'post',
    data: data
  })
}

// 删除站内信消息
export function delSiteRecords(msgSiteId) {
  return request({
    url: '/message/siteRecords/' + msgSiteId,
    method: 'delete'
  })
}

// 查询自己的站内信消息列表
export function selfPullLast(lastId) {
  return request({
    url: '/message/siteRecords/selfPullLast',
    method: 'get',
    params: { lastId }
  })
}

// 查询自己的站内信消息列表
export function selfPullReLast(lastId) {
  return request({
    url: '/message/siteRecords/selfPullReLast',
    method: 'get',
    params: { lastId }
  })
}
// 已读自己的站内信消息
export function readSelfSiteRecords(msgSiteId) {
  return request({
    url: '/message/siteRecords/selfRead/' + msgSiteId,
    method: 'put'
  })
}
// 删除自己的站内信消息
export function delSelfSiteRecords(msgSiteId) {
  return request({
    url: '/message/siteRecords/selfRemove/' + msgSiteId,
    method: 'delete'
  })
}
// 获得自未读的消息数量
export function readSelfUnReadCount() {
  return request({
    url: '/message/siteRecords/selfUnReadCount',
    method: 'get'
  })
}
