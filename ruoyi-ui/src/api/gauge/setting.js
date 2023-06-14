import request from '@/utils/request'

// 查询心理测评普通设置列表
export function listSetting(query) {
  return request({
    url: '/gauge/setting/list',
    method: 'get',
    params: query
  })
}

// 查询心理测评普通设置列表
export function listSettingByIds(ids) {
  return request({
    url: '/gauge/setting/list/by/'+ids,
    method: 'get'
  
  })
}

// 查询心理测评普通设置详细
export function getSetting(id) {
  return request({
    url: '/gauge/setting/' + id,
    method: 'get'
  })
}

// 新增心理测评普通设置
export function addSetting(data) {
  return request({
    url: '/gauge/setting',
    method: 'post',
    data: data
  })
}

// 修改心理测评普通设置
export function updateSetting(data) {
  return request({
    url: '/gauge/setting',
    method: 'put',
    data: data
  })
}

// 删除心理测评普通设置
export function delSetting(id) {
  return request({
    url: '/gauge/setting/' + id,
    method: 'delete'
  })
}
