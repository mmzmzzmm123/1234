import request from '@/utils/request'

// 查询【请填写功能名称】列表
export function listOperatorRation(query) {
  return request({
    url: '/system/operatorRation/list',
    method: 'get',
    params: query
  })
}

// 查询【请填写功能名称】详细
export function getOperatorRation(mId) {
  return request({
    url: '/system/operatorRation/' + mId,
    method: 'get'
  })
}

// 新增【请填写功能名称】
export function addOperatorRation(data) {
  return request({
    url: '/system/operatorRation',
    method: 'post',
    data: data
  })
}

// 修改【请填写功能名称】
export function updateOperatorRation(data) {
  return request({
    url: '/system/operatorRation',
    method: 'put',
    data: data
  })
}

// 删除【请填写功能名称】
export function delOperatorRation(mId) {
  return request({
    url: '/system/operatorRation/' + mId,
    method: 'delete'
  })
}
