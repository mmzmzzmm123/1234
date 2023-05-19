import request from '@/utils/request'

// 查询提交材料参数列表
export function listMaterial(query) {
  return request({
    url: '/stu/material/list',
    method: 'get',
    params: query
  })
}

// 查询提交材料参数详细
export function getMaterial(id) {
  return request({
    url: '/stu/material/' + id,
    method: 'get'
  })
}

// 新增提交材料参数
export function addMaterial(data) {
  return request({
    url: '/stu/material',
    method: 'post',
    data: data
  })
}

// 修改提交材料参数
export function updateMaterial(data) {
  return request({
    url: '/stu/material',
    method: 'put',
    data: data
  })
}

// 删除提交材料参数
export function delMaterial(id) {
  return request({
    url: '/stu/material/' + id,
    method: 'delete'
  })
}
