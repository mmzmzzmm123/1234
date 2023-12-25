import request from '@/utils/request'

export function getSrcheck(id) {
  return request({
    url: '/system/installmentpayment/getSrcheck/' + id,
    method: 'get',
  })
}

export function getInstall(id) {
    return request({
      url: '/system/installmentpayment/getInstall/' + id,
      method: 'get',
    })
  }

// 查询查询列表
export function listInstallmentpayment(query) {
  return request({
    url: '/system/installmentpayment/list',
    method: 'get',
    params: query
  })
}

// 查询查询详细
export function getInstallmentpayment(id) {
  return request({
    url: '/system/installmentpayment/' + id,
    method: 'get'
  })
}

// 新增查询
export function addInstallmentpayment(data) {
  return request({
    url: '/system/installmentpayment',
    method: 'post',
    data: data
  })
}

// 修改查询
export function updateInstallmentpayment(data) {
  return request({
    url: '/system/installmentpayment',
    method: 'put',
    data: data
  })
}

// 删除查询
export function delInstallmentpayment(id) {
  return request({
    url: '/system/installmentpayment/' + id,
    method: 'delete'
  })
}
