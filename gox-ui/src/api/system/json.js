import request from '@/utils/request'

// export function getTableTitle(nodeId, deptId) {
//   return request({
//     url: '/system/json/table-title/' + nodeId + '/' + deptId,
//     method: 'get',
//   })
// }

// 查询单json存储列表
export function listJson(query) {
  return request({
    url: '/system/json/list',
    method: 'get',
    params: query
  })
}

// 获取一个id
export function getId() {
  return request({
    url: '/system/json/id',
    method: 'post'
  })
}

//查询表单字段
export function getTableField(nodeId, deptId) {
  return request({
    url: '/system/json/table-field/' + nodeId + '/' + deptId,
    method: 'get',
  })
}

//排序
export function order(forms) {
  return request({
    url: '/system/json/order',
    method: 'put',
    data: forms
  })
}

// 查询表单json存储详细
export function getJson(id) {
  return request({
    url: '/system/json/' + id,
    method: 'get'
  })
}
export function copyJson(data){
  return request({
    url: '/system/json/copy',
    method: 'post',
    data: data
  })
}
// 新增单json存储
export function addJson(data) {
  return request({
    url: '/system/json',
    method: 'post',
    data: data
  })
}

// 修改单json存储
export function updateJson(data) {
  return request({
    url: '/system/json',
    method: 'put',
    data: data
  })
}

// 修改单json存储
export function updateTableField(data) {
  return request({
    url: '/system/json/table-field',
    method: 'put',
    data: data
  })
}

// 删除单json存储
export function delJson(id) {
  return request({
    url: '/system/json/' + id,
    method: 'delete'
  })
}

// 导出单json存储
export function exportJson(query) {
  return request({
    url: '/system/json/export',
    method: 'get',
    params: query
  })
}
