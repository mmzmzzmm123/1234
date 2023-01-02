import request from '@/utils/request'

// 查询【请填写功能名称】列表
export function listNetRation(query) {
  return request({
    url: '/system/netRation/list',
    method: 'get',
    params: query
  })
}

// 查询【请填写功能名称】详细
export function getNetRation(mId) {
  return request({
    url: '/system/netRation/' + mId,
    method: 'get'
  })
}

// 新增【请填写功能名称】
export function addNetRation(data) {
  return request({
    url: '/system/netRation',
    method: 'post',
    data: data
  })
}

// 修改【请填写功能名称】
export function updateNetRation(data) {
  return request({
    url: '/system/netRation',
    method: 'put',
    data: data
  })
}

// 删除【请填写功能名称】
export function delNetRation(mId) {
  return request({
    url: '/system/netRation/' + mId,
    method: 'delete'
  })
}
