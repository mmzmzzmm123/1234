import request from '@/utils/request'

// 查询儿童学习与发展档案（教师）列表
export function listTeacher(query) {
  return request({
    url: '/benyi/learndevelopmentteacher/list',
    method: 'get',
    params: query
  })
}

// 查询儿童学习与发展档案（教师）详细
export function getTeacher(id) {
  return request({
    url: '/benyi/learndevelopmentteacher/' + id,
    method: 'get'
  })
}

// 新增儿童学习与发展档案（教师）
export function addTeacher(data) {
  return request({
    url: '/benyi/learndevelopmentteacher',
    method: 'post',
    data: data
  })
}

// 修改儿童学习与发展档案（教师）
export function updateTeacher(data) {
  return request({
    url: '/benyi/learndevelopmentteacher',
    method: 'put',
    data: data
  })
}

// 删除儿童学习与发展档案（教师）
export function delTeacher(id) {
  return request({
    url: '/benyi/learndevelopmentteacher/' + id,
    method: 'delete'
  })
}

// 导出儿童学习与发展档案（教师）
export function exportTeacher(query) {
  return request({
    url: '/benyi/learndevelopmentteacher/export',
    method: 'get',
    params: query
  })
}
