import request from '@/utils/request'

// 查询群体教师考核过程文件数据列表
export function listQtjskhgcwjsj(query) {
  return request({
    url: '/qtjskhgl/qtjskhgcwjsj/list',
    method: 'get',
    params: query
  })
}

// 查询群体教师考核过程文件数据详细
export function getQtjskhgcwjsj(id) {
  return request({
    url: '/qtjskhgl/qtjskhgcwjsj/' + id,
    method: 'get'
  })
}

// 新增群体教师考核过程文件数据
export function addQtjskhgcwjsj(data) {
  return request({
    url: '/qtjskhgl/qtjskhgcwjsj',
    method: 'post',
    data: data
  })
}

// 修改群体教师考核过程文件数据
export function updateQtjskhgcwjsj(data) {
  return request({
    url: '/qtjskhgl/qtjskhgcwjsj',
    method: 'put',
    data: data
  })
}

// 删除群体教师考核过程文件数据
export function delQtjskhgcwjsj(id) {
  return request({
    url: '/qtjskhgl/qtjskhgcwjsj/' + id,
    method: 'delete'
  })
}

// 导出群体教师考核过程文件数据
export function exportQtjskhgcwjsj(query) {
  return request({
    url: '/qtjskhgl/qtjskhgcwjsj/export',
    method: 'get',
    params: query
  })
}