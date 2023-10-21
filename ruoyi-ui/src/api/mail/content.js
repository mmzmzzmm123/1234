import request from '@/utils/request'

// 查询晚安语列表列表
export function listContent(query) {
  return request({
    url: '/mail/content/list',
    method: 'get',
    params: query
  })
}

// 查询晚安语列表详细
export function getContent(id) {
  return request({
    url: '/mail/content/' + id,
    method: 'get'
  })
}

// 新增晚安语列表
export function addContent(data) {
  return request({
    url: '/mail/content',
    method: 'post',
    data: data
  })
}

// 修改晚安语列表
export function updateContent(data) {
  return request({
    url: '/mail/content',
    method: 'put',
    data: data
  })
}

// 删除晚安语列表
export function delContent(id) {
  return request({
    url: '/mail/content/' + id,
    method: 'delete'
  })
}
