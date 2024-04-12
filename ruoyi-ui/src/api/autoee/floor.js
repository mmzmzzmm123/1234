import request from '@/utils/request'

// 查询楼层管理列表
export function listFloor(query) {
  return request({
    url: '/autoee/floor/list',
    method: 'get',
    params: query
  })
}

// 查询楼层管理详细
export function getFloor(id) {
  return request({
    url: '/autoee/floor/' + id,
    method: 'get'
  })
}

// 新增楼层管理
export function addFloor(data) {
  return request({
    url: '/autoee/floor',
    method: 'post',
    data: data
  })
}

// 修改楼层管理
export function updateFloor(data) {
  return request({
    url: '/autoee/floor',
    method: 'put',
    data: data
  })
}

// 删除楼层管理
export function delFloor(id) {
  return request({
    url: '/autoee/floor/' + id,
    method: 'delete'
  })
}
