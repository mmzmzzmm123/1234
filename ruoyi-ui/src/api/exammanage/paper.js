import request from '@/utils/request'

// 查询考试记录列表
export function listPaper(query) {
  return request({
    url: '/exammanage/paper/list',
    method: 'get',
    params: query
  })
}

// 查询考试记录详细
export function getPaper(id) {
  return request({
    url: '/exammanage/paper/' + id,
    method: 'get'
  })
}

// 新增考试记录
export function addPaper(data) {
  return request({
    url: '/exammanage/paper',
    method: 'post',
    data: data
  })
}

// 修改考试记录
export function updatePaper(data) {
  return request({
    url: '/exammanage/paper',
    method: 'put',
    data: data
  })
}

// 删除考试记录
export function delPaper(id) {
  return request({
    url: '/exammanage/paper/' + id,
    method: 'delete'
  })
}
