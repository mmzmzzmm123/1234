import request from '@/utils/request'

// 查询
export function listTable(query) {
  return request({
    url: '/tool/gen/list',
    method: 'post',
    params: query
  })
}
// 查询db数据库列表
export function listdbTable(query) {
  return request({
    url: '/tool/gen/db/list',
    method: 'post',
    params: query
  })
}
// 导出table
export function exportTable(data) {
  return request({
    url: '/tool/gen/list/'+data,
    method: 'get',
  })
}
// 查询更改表信息
export function editTableInfo(data) {
  return request({
    url: '/tool/gen/edit',
    method: 'get',
    params:data
  })
}

// 更改表请求
export function editGenInfo(data) {
  return request({
    url: '/tool/gen/edit',
    method: 'post',
    data:data
  })
}
// 导入表
export function importTable(data) {
  return request({
    url: '/tool/gen/importTable',
    method: 'post',
    params:data
  })
}
// 预览
export function previewTable(data) {
  return request({
    url: '/tool/gen/preview/'+data,
    method: 'get'
  })
}
// 移除tableid
export function removeTable(data) {
  return request({
    url: '/tool/gen/remove',
    method: 'post',
    params:data
  })
}

