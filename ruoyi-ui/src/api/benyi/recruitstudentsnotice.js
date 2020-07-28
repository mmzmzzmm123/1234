import request from '@/utils/request'

// 查询入园通知书列表
export function listNotice(query) {
  return request({
    url: '/benyi/recruitstudentsnotice/list',
    method: 'get',
    params: query
  })
}

// 查询入园通知书详细
export function getNotice(id) {
  return request({
    url: '/benyi/recruitstudentsnotice/' + id,
    method: 'get'
  })
}

// 新增入园通知书
export function addNotice(data) {
  return request({
    url: '/benyi/recruitstudentsnotice',
    method: 'post',
    data: data
  })
}

// 修改入园通知书
export function updateNotice(data) {
  return request({
    url: '/benyi/recruitstudentsnotice',
    method: 'put',
    data: data
  })
}

// 删除入园通知书
export function delNotice(id) {
  return request({
    url: '/benyi/recruitstudentsnotice/' + id,
    method: 'delete'
  })
}

// 复制入园通知书
export function copyNotice(id) {
  return request({
    url: '/benyi/recruitstudentsnotice/copy/' + id,
    method: 'post'
  })
}

// 导出入园通知书
export function exportNotice(query) {
  return request({
    url: '/benyi/recruitstudentsnotice/export',
    method: 'get',
    params: query
  })
}
