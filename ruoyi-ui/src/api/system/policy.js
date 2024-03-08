import request from '@/utils/request'

// 查询出口国家政策列表
export function listPolicy(query) {
  return request({
    url: '/system/policy/list',
    method: 'get',
    params: query
  })
}

// 查询出口国家政策详细
export function getPolicy(id) {
  return request({
    url: '/system/policy/' + id,
    method: 'get'
  })
}

// 新增出口国家政策
export function addPolicy(data) {
  return request({
    url: '/system/policy',
    method: 'post',
    data: data
  })
}

// 修改出口国家政策
export function updatePolicy(data) {
  return request({
    url: '/system/policy',
    method: 'put',
    data: data
  })
}

// 删除出口国家政策
export function delPolicy(id) {
  return request({
    url: '/system/policy/' + id,
    method: 'delete'
  })
}
