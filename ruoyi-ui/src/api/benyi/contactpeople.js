import request from '@/utils/request'

// 查询幼儿紧急情况联系人列表
export function listContactpeople(query) {
  return request({
    url: '/benyi/contactpeople/list',
    method: 'get',
    params: query
  })
}

// 查询幼儿紧急情况联系人详细
export function getContactpeople(id) {
  return request({
    url: '/benyi/contactpeople/' + id,
    method: 'get'
  })
}


// 查询幼儿紧急情况联系人详细
export function getContactpeopleByChildId(id) {
  return request({
    url: '/benyi/contactpeople/bychild/' + id,
    method: 'get'
  })
}

// 新增幼儿紧急情况联系人
export function addContactpeople(data) {
  return request({
    url: '/benyi/contactpeople',
    method: 'post',
    data: data
  })
}

// 修改幼儿紧急情况联系人
export function updateContactpeople(data) {
  return request({
    url: '/benyi/contactpeople',
    method: 'put',
    data: data
  })
}

// 删除幼儿紧急情况联系人
export function delContactpeople(id) {
  return request({
    url: '/benyi/contactpeople/' + id,
    method: 'delete'
  })
}

// 导出幼儿紧急情况联系人
export function exportContactpeople(query) {
  return request({
    url: '/benyi/contactpeople/export',
    method: 'get',
    params: query
  })
}