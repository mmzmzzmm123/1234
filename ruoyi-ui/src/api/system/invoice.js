import request from '@/utils/request'

// 查询【请填写功能名称】列表
export function listInvoice(query) {
  return request({
    url: '/system/invoice/list',
    method: 'get',
    params: query
  })
}

// 查询【请填写功能名称】详细
export function getInvoice(id) {
  return request({
    url: '/system/invoice/' + id,
    method: 'get'
  })
}

// 新增【请填写功能名称】
export function addInvoice(data) {
  return request({
    url: '/system/invoice',
    method: 'post',
    data: data
  })
}

// 修改【请填写功能名称】
export function updateInvoice(data) {
  return request({
    url: '/system/invoice',
    method: 'put',
    data: data
  })
}

// 删除【请填写功能名称】
export function delInvoice(id) {
  return request({
    url: '/system/invoice/' + id,
    method: 'delete'
  })
}
