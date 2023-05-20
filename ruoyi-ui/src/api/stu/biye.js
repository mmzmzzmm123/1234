import request from '@/utils/request'

// 修改材料提交状态
export function updateMaterialFlag(data){
  return request({
    url:'/stu/biye/updateMateriaFlag',
    method:'post',
    data:data
  })
}

// 查询毕业实践列表
export function getList(query) {
  return request({
    url: '/stu/biye/list',
    method: 'get',
    params: query
  })
}

// 查询毕业实践详细
export function getBiye(id) {
  return request({
    url: '/stu/biye/' + id,
    method: 'get'
  })
}

// 新增毕业实践
export function addBiye(data) {
  return request({
    url: '/stu/biye',
    method: 'post',
    data: data
  })
}

// 修改毕业实践
export function updateBiye(data) {
  return request({
    url: '/stu/biye',
    method: 'put',
    data: data
  })
}

// 删除毕业实践
export function delBiye(id) {
  return request({
    url: '/stu/biye/' + id,
    method: 'delete'
  })
}
