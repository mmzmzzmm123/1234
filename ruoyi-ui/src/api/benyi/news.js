import request from '@/utils/request'

// 查询新闻中心列表
export function listNews(query) {
  return request({
    url: '/benyi/news/list',
    method: 'get',
    params: query
  })
}

// 查询新闻中心详细
export function getNews(id) {
  return request({
    url: '/benyi/news/' + id,
    method: 'get'
  })
}

// 新增新闻中心
export function addNews(data) {
  return request({
    url: '/benyi/news',
    method: 'post',
    data: data
  })
}

// 修改新闻中心
export function updateNews(data) {
  return request({
    url: '/benyi/news',
    method: 'put',
    data: data
  })
}

// 删除新闻中心
export function delNews(id) {
  return request({
    url: '/benyi/news/' + id,
    method: 'delete'
  })
}

// 导出新闻中心
export function exportNews(query) {
  return request({
    url: '/benyi/news/export',
    method: 'get',
    params: query
  })
}