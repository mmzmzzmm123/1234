import request from '@/utils/request'

export function getAuthPay(id) {
  return request({
    url: '/system/srchecksheet/getInfo/' + id,
    method: 'get'
  })
}

// 查询【请填写功能名称】列表
export function listSrchecksheet(query) {
  return request({
    url: '/system/srchecksheet/list',
    method: 'get',
    params: query
  })
}

// 查询【请填写功能名称】详细
export function getSrchecksheet(id) {
  return request({
    url: '/system/srchecksheet/getInfo/' + id,
    method: 'get'
  })
}

// 新增【请填写功能名称】
export function addSrchecksheet(data) {
  return request({
    url: '/system/srchecksheet',
    method: 'post',
    data: data
  })
}

// 修改【请填写功能名称】
export function updateSrchecksheet(data) {
  return request({
    url: '/system/srchecksheet',
    method: 'put',
    data: data
  })
}

// 删除【请填写功能名称】
export function delSrchecksheet(id) {
  return request({
    url: '/system/srchecksheet/' + id,
    method: 'delete'
  })
}
