import request from '@/utils/request'

// 查询【请填写功能名称】列表
export function listIosRation(query) {
  return request({
    url: '/system/iosRation/list',
    method: 'get',
    params: query
  })
}

// 查询【请填写功能名称】详细
export function getIosRation(mId) {
  return request({
    url: '/system/iosRation/' + mId,
    method: 'get'
  })
}

// 新增【请填写功能名称】
export function addIosRation(data) {
  return request({
    url: '/system/iosRation',
    method: 'post',
    data: data
  })
}

// 修改【请填写功能名称】
export function updateIosRation(data) {
  return request({
    url: '/system/iosRation',
    method: 'put',
    data: data
  })
}

// 删除【请填写功能名称】
export function delIosRation(mId) {
  return request({
    url: '/system/iosRation/' + mId,
    method: 'delete'
  })
}
