import request from '@/utils/request'

// 查询群体教师考核方案列表
export function listQtjskhfa(query) {
  return request({
    url: '/qtjskhgl/qtjskhfa/list',
    method: 'get',
    params: query
  })
}

// 查询群体教师考核方案详细
export function getQtjskhfa(id) {
  return request({
    url: '/qtjskhgl/qtjskhfa/' + id,
    method: 'get'
  })
}

// 新增群体教师考核方案
export function addQtjskhfa(data) {
  return request({
    url: '/qtjskhgl/qtjskhfa',
    method: 'post',
    data: data
  })
}

// 修改群体教师考核方案
export function updateQtjskhfa(data) {
  return request({
    url: '/qtjskhgl/qtjskhfa',
    method: 'put',
    data: data
  })
}

// 删除群体教师考核方案
export function delQtjskhfa(id) {
  return request({
    url: '/qtjskhgl/qtjskhfa/' + id,
    method: 'delete'
  })
}

// 导出群体教师考核方案
export function exportQtjskhfa(query) {
  return request({
    url: '/qtjskhgl/qtjskhfa/export',
    method: 'get',
    params: query
  })
}