import request from '@/utils/request'

// 查询儿童学习与发展档案（家长）列表
export function listFamily(query) {
  return request({
    url: '/benyi/learndevelopmentfamily/list',
    method: 'get',
    params: query
  })
}

// 查询儿童学习与发展档案（家长）详细
export function getFamily(id) {
  return request({
    url: '/benyi/learndevelopmentfamily/' + id,
    method: 'get'
  })
}

// 新增儿童学习与发展档案（家长）
export function addFamily(data) {
  return request({
    url: '/benyi/learndevelopmentfamily',
    method: 'post',
    data: data
  })
}

// 修改儿童学习与发展档案（家长）
export function updateFamily(data) {
  return request({
    url: '/benyi/learndevelopmentfamily',
    method: 'put',
    data: data
  })
}

// 删除儿童学习与发展档案（家长）
export function delFamily(id) {
  return request({
    url: '/benyi/learndevelopmentfamily/' + id,
    method: 'delete'
  })
}

// 导出儿童学习与发展档案（家长）
export function exportFamily(query) {
  return request({
    url: '/benyi/learndevelopmentfamily/export',
    method: 'get',
    params: query
  })
}