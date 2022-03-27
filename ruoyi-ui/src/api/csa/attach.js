import request from '@/utils/request'

// 查询菜地日志附件列表
export function listAttach(query) {
  return request({
    url: '/csa/attach/list',
    method: 'get',
    params: query
  })
}

// 查询菜地日志附件详细
export function getAttach(id) {
  return request({
    url: '/csa/attach/' + id,
    method: 'get'
  })
}

// 新增菜地日志附件
export function addAttach(data) {
  return request({
    url: '/csa/attach',
    method: 'post',
    data: data
  })
}

// 修改菜地日志附件
export function updateAttach(data) {
  return request({
    url: '/csa/attach',
    method: 'put',
    data: data
  })
}

// 删除菜地日志附件
export function delAttach(id) {
  return request({
    url: '/csa/attach/' + id,
    method: 'delete'
  })
}
