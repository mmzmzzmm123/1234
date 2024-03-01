import request from '@/utils/request'

// 查询政策信息管理列表
export function listPolicy(query) {
  return request({
    url: '/api/policy/list',
    method: 'get',
    params: query
  })
}

// 查询政策信息管理详细
export function getPolicy(id) {
  return request({
    url: '/api/policy/' + id,
    method: 'get'
  })
}

// 新增政策信息管理
export function addPolicy(data) {
  return request({
    url: '/api/policy',
    method: 'post',
    data: data
  })
}

// 修改政策信息管理
export function updatePolicy(data) {
  return request({
    url: '/api/policy',
    method: 'put',
    data: data
  })
}

// 删除政策信息管理
export function delPolicy(id) {
  return request({
    url: '/api/policy/' + id,
    method: 'delete'
  })
}
