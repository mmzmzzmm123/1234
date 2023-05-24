import request from '@/utils/request'

// 查询班级年级参数列表
export function listBase(query) {
  return request({
    url: '/stu/base/list',
    method: 'get',
    params: query
  })
}

export function listBaseAll(query) {
  return  request({
    url: '/stu/base/listAll',
    method: 'get',
    params: query
  })
}


// 查询班级年级参数详细
export function getBase(id) {
  return request({
    url: '/stu/base/' + id,
    method: 'get'
  })
}

// 新增班级年级参数
export function addBase(data) {
  return request({
    url: '/stu/base',
    method: 'post',
    data: data
  })
}

// 修改班级年级参数
export function updateBase(data) {
  return request({
    url: '/stu/base',
    method: 'put',
    data: data
  })
}

// 删除班级年级参数
export function delBase(id) {
  return request({
    url: '/stu/base/' + id,
    method: 'delete'
  })
}
