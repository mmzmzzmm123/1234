import request from '@/utils/request'

// 查询水质记录列表
export function listRecord(query) {
  return request({
    url: '/usv/record/list',
    method: 'get',
    params: query
  })
}

// 查询水质记录详细
export function getRecord(id) {
  return request({
    url: '/usv/record/' + id,
    method: 'get'
  })
}

// 新增水质记录
export function addRecord(data) {
  return request({
    url: '/usv/record',
    method: 'post',
    data: data
  })
}

// 修改水质记录
export function updateRecord(data) {
  return request({
    url: '/usv/record',
    method: 'put',
    data: data
  })
}

// 删除水质记录
export function delRecord(id) {
  return request({
    url: '/usv/record/' + id,
    method: 'delete'
  })
}

// 导出水质记录
export function exportRecord(query) {
  return request({
    url: '/usv/record/export',
    method: 'get',
    params: query
  })
}