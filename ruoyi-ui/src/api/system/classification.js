import request from '@/utils/request'

// 查询案例分类列表
export function listClassification(query) {
  return request({
    url: '/system/classification/list',
    method: 'get',
    params: query
  })
}

// 查询案例分类详细
export function getClassification(id) {
  return request({
    url: '/system/classification/' + id,
    method: 'get'
  })
}

// 新增案例分类
export function addClassification(data) {
  return request({
    url: '/system/classification',
    method: 'post',
    data: data
  })
}

// 修改案例分类
export function updateClassification(data) {
  return request({
    url: '/system/classification',
    method: 'put',
    data: data
  })
}

// 删除案例分类
export function delClassification(id) {
  return request({
    url: '/system/classification/' + id,
    method: 'delete'
  })
}
