import request from '@/utils/request'

// 查询生日溥列表
export function listBirthdaypu(query) {
  return request({
    url: '/system/birthdaypu/list',
    method: 'get',
    params: query
  })
}

// 查询生日溥详细
export function getBirthdaypu(id) {
  return request({
    url: '/system/birthdaypu/' + id,
    method: 'get'
  })
}

// 新增生日溥
export function addBirthdaypu(data) {
  return request({
    url: '/system/birthdaypu',
    method: 'post',
    data: data
  })
}

// 修改生日溥
export function updateBirthdaypu(data) {
  return request({
    url: '/system/birthdaypu',
    method: 'put',
    data: data
  })
}

// 删除生日溥
export function delBirthdaypu(id) {
  return request({
    url: '/system/birthdaypu/' + id,
    method: 'delete'
  })
}

// 导出生日溥
export function exportBirthdaypu(query) {
  return request({
    url: '/system/birthdaypu/export',
    method: 'get',
    params: query
  })
}