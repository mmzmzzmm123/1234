import request from '@/utils/request'

// 查询幼儿园机构列表
export function listSchool(query) {
  return request({
    url: '/system/school/list',
    method: 'get',
    params: query
  })
}

// 查询幼儿园机构详细
export function getSchool(id) {
  return request({
    url: '/system/school/' + id,
    method: 'get'
  })
}

// 新增幼儿园机构
export function addSchool(data) {
  return request({
    url: '/system/school',
    method: 'post',
    data: data
  })
}

// 修改幼儿园机构
export function updateSchool(data) {
  return request({
    url: '/system/school',
    method: 'put',
    data: data
  })
}

// 删除幼儿园机构
export function delSchool(id) {
  return request({
    url: '/system/school/' + id,
    method: 'delete'
  })
}

// 导出幼儿园机构
export function exportSchool(query) {
  return request({
    url: '/system/school/export',
    method: 'get',
    params: query
  })
}