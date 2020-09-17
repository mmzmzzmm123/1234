import request from '@/utils/request'

// 查询群体教师考核审核过程列表
export function listQtjskhsh(query) {
  return request({
    url: '/qtjskhgl/qtjskhsh/list',
    method: 'get',
    params: query
  })
}

// 查询群体教师考核审核过程详细
export function getQtjskhsh(id) {
  return request({
    url: '/qtjskhgl/qtjskhsh/' + id,
    method: 'get'
  })
}

// 新增群体教师考核审核过程
export function addQtjskhsh(data) {
  return request({
    url: '/qtjskhgl/qtjskhsh',
    method: 'post',
    data: data
  })
}

// 修改群体教师考核审核过程
export function updateQtjskhsh(data) {
  return request({
    url: '/qtjskhgl/qtjskhsh',
    method: 'put',
    data: data
  })
}

// 删除群体教师考核审核过程
export function delQtjskhsh(id) {
  return request({
    url: '/qtjskhgl/qtjskhsh/' + id,
    method: 'delete'
  })
}

// 导出群体教师考核审核过程
export function exportQtjskhsh(query) {
  return request({
    url: '/qtjskhgl/qtjskhsh/export',
    method: 'get',
    params: query
  })
}