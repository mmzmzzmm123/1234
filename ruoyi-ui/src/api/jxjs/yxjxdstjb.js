import request from '@/utils/request'

// 查询优秀见习导师推荐列表
export function listYxjxdstjb(query) {
  return request({
    url: '/jxjs/yxjxdstjb/list',
    method: 'get',
    params: query
  })
}

// 查询优秀见习导师推荐详细
export function getYxjxdstjb(id) {
  return request({
    url: '/jxjs/yxjxdstjb/' + id,
    method: 'get'
  })
}

// 新增优秀见习导师推荐
export function addYxjxdstjb(data) {
  return request({
    url: '/jxjs/yxjxdstjb',
    method: 'post',
    data: data
  })
}

// 修改优秀见习导师推荐
export function updateYxjxdstjb(data) {
  return request({
    url: '/jxjs/yxjxdstjb',
    method: 'put',
    data: data
  })
}

// 删除优秀见习导师推荐
export function delYxjxdstjb(id) {
  return request({
    url: '/jxjs/yxjxdstjb/' + id,
    method: 'delete'
  })
}

// 导出优秀见习导师推荐
export function exportYxjxdstjb(query) {
  return request({
    url: '/jxjs/yxjxdstjb/export',
    method: 'get',
    params: query
  })
}