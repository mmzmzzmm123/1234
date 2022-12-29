import request from '@/utils/request'

// 查询【请填写功能名称】列表
export function listEnterTimeRation(query) {
  return request({
    url: '/system/enterTimeRation/list',
    method: 'get',
    params: query
  })
}

// 查询【请填写功能名称】详细
export function getEnterTimeRation(mId) {
  return request({
    url: '/system/enterTimeRation/' + mId,
    method: 'get'
  })
}

// 新增【请填写功能名称】
export function addEnterTimeRation(data) {
  return request({
    url: '/system/enterTimeRation',
    method: 'post',
    data: data
  })
}

// 修改【请填写功能名称】
export function updateEnterTimeRation(data) {
  return request({
    url: '/system/enterTimeRation',
    method: 'put',
    data: data
  })
}

// 删除【请填写功能名称】
export function delEnterTimeRation(mId) {
  return request({
    url: '/system/enterTimeRation/' + mId,
    method: 'delete'
  })
}
