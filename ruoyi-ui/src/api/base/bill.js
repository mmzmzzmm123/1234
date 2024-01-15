import request from '@/utils/request'

// 查询原数据---0号台账列表
export function listBill(query) {
  return request({
    url: '/source/zeroBill/list',
    method: 'get',
    params: query
  })
}

// 查询原数据---0号台账详细
export function getBill(id) {
  return request({
    url: '/source/zeroBill/' + id,
    method: 'get'
  })
}

// 新增原数据---0号台账
export function addBill(data) {
  return request({
    url: '/source/zeroBill/saveOrUpdate',
    method: 'post',
    data: data
  })
}

// 修改原数据---0号台账
export function updateBill(data) {
  return request({
    url: '/source/zeroBill/saveOrUpdate',
    method: 'put',
    data: data
  })
}

// 删除原数据---0号台账
export function delBill(id) {
  return request({
    url: '/source/zeroBill/' + id,
    method: 'delete'
  })
}
// 导入表
export function importTable(data) {
  return request({
    url: '/source/zeroBill/import',
    method: 'post',
    data: data
  })
}
