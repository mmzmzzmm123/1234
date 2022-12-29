import request from '@/utils/request'

// 查询【请填写功能名称】列表
export function listPhoneRation(query) {
  return request({
    url: '/system/phoneRation/list',
    method: 'get',
    params: query
  })
}

// 查询【请填写功能名称】详细
export function getPhoneRation(mId) {
  return request({
    url: '/system/phoneRation/' + mId,
    method: 'get'
  })
}

// 新增【请填写功能名称】
export function addPhoneRation(data) {
  return request({
    url: '/system/phoneRation',
    method: 'post',
    data: data
  })
}

// 修改【请填写功能名称】
export function updatePhoneRation(data) {
  return request({
    url: '/system/phoneRation',
    method: 'put',
    data: data
  })
}

// 删除【请填写功能名称】
export function delPhoneRation(mId) {
  return request({
    url: '/system/phoneRation/' + mId,
    method: 'delete'
  })
}
