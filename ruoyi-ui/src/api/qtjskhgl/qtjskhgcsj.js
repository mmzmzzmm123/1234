import request from '@/utils/request'

// 查询群体教师考核过程数据列表
export function listQtjskhgcsj(query) {
  return request({
    url: '/qtjskhgl/qtjskhgcsj/list',
    method: 'get',
    params: query
  })
}

// 查询群体教师考核过程数据详细
export function getQtjskhgcsj(id) {
  return request({
    url: '/qtjskhgl/qtjskhgcsj/' + id,
    method: 'get'
  })
}

// 新增群体教师考核过程数据
export function addQtjskhgcsj(data) {
  return request({
    url: '/qtjskhgl/qtjskhgcsj',
    method: 'post',
    data: data
  })
}

// 修改群体教师考核过程数据
export function updateQtjskhgcsj(data) {
  return request({
    url: '/qtjskhgl/qtjskhgcsj',
    method: 'put',
    data: data
  })
}

// 删除群体教师考核过程数据
export function delQtjskhgcsj(id) {
  return request({
    url: '/qtjskhgl/qtjskhgcsj/' + id,
    method: 'delete'
  })
}

// 导出群体教师考核过程数据
export function exportQtjskhgcsj(query) {
  return request({
    url: '/qtjskhgl/qtjskhgcsj/export',
    method: 'get',
    params: query
  })
}