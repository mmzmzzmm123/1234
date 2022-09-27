import request from '@/utils/request'

// 查询心理测评多维设置列表
export function listMultiSetting(query) {
  return request({
    url: '/gauge/multiSetting/list',
    method: 'get',
    params: query
  })
}

// 查询心理测评多维设置详细
export function getMultiSetting(id) {
  return request({
    url: '/gauge/multiSetting/' + id,
    method: 'get'
  })
}

// 新增心理测评多维设置
export function addMultiSetting(data) {
  return request({
    url: '/gauge/multiSetting',
    method: 'post',
    data: data
  })
}

// 修改心理测评多维设置
export function updateMultiSetting(data) {
  return request({
    url: '/gauge/multiSetting',
    method: 'put',
    data: data
  })
}

// 删除心理测评多维设置
export function delMultiSetting(id) {
  return request({
    url: '/gauge/multiSetting/' + id,
    method: 'delete'
  })
}
