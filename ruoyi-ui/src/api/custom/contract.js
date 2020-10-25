import request from '@/utils/request'

// 查询合同列表
export function listContract(query) {
  return request({
    url: '/custom/contract/list',
    method: 'get',
    params: query
  })
}

// 查询合同详细
export function getContract(id) {
  return request({
    url: '/custom/contract/' + id,
    method: 'get'
  })
}

// 新增合同
export function addContract(data) {
  return request({
    url: '/custom/contract',
    method: 'post',
    data: data
  })
}

// 修改合同
export function updateContract(data) {
  return request({
    url: '/custom/contract',
    method: 'put',
    data: data
  })
}

// 删除合同
export function delContract(id) {
  return request({
    url: '/custom/contract/' + id,
    method: 'delete'
  })
}

// 导出合同
export function exportContract(query) {
  return request({
    url: '/custom/contract/export',
    method: 'get',
    params: query
  })
}

export function getFile(id) {
  return request({
    url: '/custom/contract/file/' + id,
    method: 'get',
  })
}
