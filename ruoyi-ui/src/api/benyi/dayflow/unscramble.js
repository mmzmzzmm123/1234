import request from '@/utils/request'

// 查询一日流程解读列表
export function listUnscramble(query) {
  return request({
    url: '/benyi/unscramble/list',
    method: 'get',
    params: query
  })
}

// 查询一日流程解读详细
export function getUnscramble(id) {
  return request({
    url: '/benyi/unscramble/' + id,
    method: 'get'
  })
}

// 新增一日流程解读
export function addUnscramble(data) {
  return request({
    url: '/benyi/unscramble',
    method: 'post',
    data: data
  })
}

// 修改一日流程解读
export function updateUnscramble(data) {
  return request({
    url: '/benyi/unscramble',
    method: 'put',
    data: data
  })
}

// 删除一日流程解读
export function delUnscramble(id) {
  return request({
    url: '/benyi/unscramble/' + id,
    method: 'delete'
  })
}

// 导出一日流程解读
export function exportUnscramble(query) {
  return request({
    url: '/benyi/unscramble/export',
    method: 'get',
    params: query
  })
}