import request from '@/utils/request'

// 查询章节列表
export function listSection(query) {
  return request({
    url: '/course/section/list',
    method: 'get',
    params: query
  })
}

// 查询章节详细
export function getSection(id) {
  return request({
    url: '/course/section/' + id,
    method: 'get'
  })
}

// 新增章节
export function addSection(data) {
  return request({
    url: '/course/section',
    method: 'post',
    data: data
  })
}

// 修改章节
export function updateSection(data) {
  return request({
    url: '/course/section',
    method: 'put',
    data: data
  })
}

// 删除章节
export function delSection(id) {
  return request({
    url: '/course/section/' + id,
    method: 'delete'
  })
}
