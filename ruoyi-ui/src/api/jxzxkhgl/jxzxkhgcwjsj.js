import request from '@/utils/request'

// 查询考核过程文件数据列表
export function listJxzxkhgcwjsj(query) {
  return request({
    url: '/jxzxkhgl/jxzxkhgcwjsj/list',
    method: 'get',
    params: query
  })
}

// 查询考核过程文件数据详细
export function getJxzxkhgcwjsj(id) {
  return request({
    url: '/jxzxkhgl/jxzxkhgcwjsj/' + id,
    method: 'get'
  })
}

// 新增考核过程文件数据
export function addJxzxkhgcwjsj(data) {
  return request({
    url: '/jxzxkhgl/jxzxkhgcwjsj',
    method: 'post',
    data: data
  })
}

// 修改考核过程文件数据
export function updateJxzxkhgcwjsj(data) {
  return request({
    url: '/jxzxkhgl/jxzxkhgcwjsj',
    method: 'put',
    data: data
  })
}

// 删除考核过程文件数据
export function delJxzxkhgcwjsj(id) {
  return request({
    url: '/jxzxkhgl/jxzxkhgcwjsj/' + id,
    method: 'delete'
  })
}

// 导出考核过程文件数据
export function exportJxzxkhgcwjsj(query) {
  return request({
    url: '/jxzxkhgl/jxzxkhgcwjsj/export',
    method: 'get',
    params: query
  })
}