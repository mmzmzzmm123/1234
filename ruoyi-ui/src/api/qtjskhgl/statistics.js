import request from '@/utils/request'

// 查询群体教师名单列表
export function listQtjsKhjd(query) {
  return request({
    url: '/qtjskhgl/qtjsstatistics/list',
    method: 'get',
    params: query
  })
}

// 查询群体教师名单详细
export function getQtjsKhjd(id) {
  return request({
    url: '/qtjskhgl/qtjsstatistics/' + id,
    method: 'get'
  })
}