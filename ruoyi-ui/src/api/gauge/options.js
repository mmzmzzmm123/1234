import request from '@/utils/request'

// 查询心理测评问题选项列表
export function listOptions(query) {
  return request({
    url: '/gauge/options/list',
    method: 'get',
    params: query
  })
}

// 查询心理测评问题选项详细
export function getOptions(id) {
  return request({
    url: '/gauge/options/' + id,
    method: 'get'
  })
}

// 新增心理测评问题选项
export function addOptions(data) {
  return request({
    url: '/gauge/options',
    method: 'post',
    data: data
  })
}

// 修改心理测评问题选项
export function updateOptions(data) {
  return request({
    url: '/gauge/options',
    method: 'put',
    data: data
  })
}

// 删除心理测评问题选项
export function delOptions(id) {
  return request({
    url: '/gauge/options/' + id,
    method: 'delete'
  })
}
