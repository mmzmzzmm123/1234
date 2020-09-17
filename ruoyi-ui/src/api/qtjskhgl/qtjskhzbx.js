import request from '@/utils/request'

// 查询群体教师考核指标项列表
export function listQtjskhzbx(query) {
  return request({
    url: '/qtjskhgl/qtjskhzbx/list',
    method: 'get',
    params: query
  })
}

// 查询群体教师考核指标项详细
export function getQtjskhzbx(id) {
  return request({
    url: '/qtjskhgl/qtjskhzbx/' + id,
    method: 'get'
  })
}

// 新增群体教师考核指标项
export function addQtjskhzbx(data) {
  return request({
    url: '/qtjskhgl/qtjskhzbx',
    method: 'post',
    data: data
  })
}

// 修改群体教师考核指标项
export function updateQtjskhzbx(data) {
  return request({
    url: '/qtjskhgl/qtjskhzbx',
    method: 'put',
    data: data
  })
}

// 删除群体教师考核指标项
export function delQtjskhzbx(id) {
  return request({
    url: '/qtjskhgl/qtjskhzbx/' + id,
    method: 'delete'
  })
}

// 导出群体教师考核指标项
export function exportQtjskhzbx(query) {
  return request({
    url: '/qtjskhgl/qtjskhzbx/export',
    method: 'get',
    params: query
  })
}