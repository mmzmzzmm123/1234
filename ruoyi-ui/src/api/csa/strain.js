import request from '@/utils/request'

// 查询菜地品种列表
export function listStrain(query) {
  return request({
    url: '/csa/strain/list',
    method: 'get',
    params: query
  })
}

// 查询菜地品种详细
export function getStrain(id) {
  return request({
    url: '/csa/strain/' + id,
    method: 'get'
  })
}

// 新增菜地品种
export function addStrain(data) {
  return request({
    url: '/csa/strain',
    method: 'post',
    data: data
  })
}

// 修改菜地品种
export function updateStrain(data) {
  return request({
    url: '/csa/strain',
    method: 'put',
    data: data
  })
}

// 删除菜地品种
export function delStrain(id) {
  return request({
    url: '/csa/strain/' + id,
    method: 'delete'
  })
}
