import request from '@/utils/request'

// 查询典型案例列表
export function listCase(query) {
  return request({
    url: '/system/case/list',
    method: 'get',
    params: query
  })
}

// 查询典型案例详细
export function getCase(id) {
  return request({
    url: '/system/case/' + id,
    method: 'get'
  })
}

// 新增典型案例
export function addCase(data) {
  return request({
    url: '/system/case',
    method: 'post',
    data: data
  })
}

// 修改典型案例
export function updateCase(data) {
  return request({
    url: '/system/case',
    method: 'put',
    data: data
  })
}

// 删除典型案例
export function delCase(id) {
  return request({
    url: '/system/case/' + id,
    method: 'delete'
  })
}
