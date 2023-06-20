import request from '@/utils/request'

// 查询咨询类型列表
export function listPsyClass(query) {
  return request({
    url: '/psychology/psyClass/list',
    method: 'get',
    params: query
  })
}

// 查询咨询类型详细
export function getPsyClass(id) {
  return request({
    url: '/psychology/psyClass/' + id,
    method: 'get'
  })
}

// 新增咨询类型
export function addPsyClass(data) {
  return request({
    url: '/psychology/psyClass',
    method: 'post',
    data: data
  })
}

// 修改咨询类型
export function updatePsyClass(data) {
  return request({
    url: '/psychology/psyClass',
    method: 'put',
    data: data
  })
}

// 删除咨询类型
export function delPsyClass(id) {
  return request({
    url: '/psychology/psyClass/' + id,
    method: 'delete'
  })
}
