import request from '@/utils/request'

// 查询建筑物管理列表
export function listBuilding(query) {
  return request({
    url: '/autoee/building/list',
    method: 'get',
    params: query
  })
}

// 查询建筑物管理详细
export function getBuilding(id) {
  return request({
    url: '/autoee/building/' + id,
    method: 'get'
  })
}

// 新增建筑物管理
export function addBuilding(data) {
  return request({
    url: '/autoee/building',
    method: 'post',
    data: data
  })
}

// 修改建筑物管理
export function updateBuilding(data) {
  return request({
    url: '/autoee/building',
    method: 'put',
    data: data
  })
}

// 删除建筑物管理
export function delBuilding(id) {
  return request({
    url: '/autoee/building/' + id,
    method: 'delete'
  })
}
