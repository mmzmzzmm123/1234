import request from '@/utils/request'

// 查询见习之星教师培训评价列表
export function listJxzxpxpj(query) {
  return request({
    url: '/jxjs/jxzxpxpj/list',
    method: 'get',
    params: query
  })
}

// 查询见习之星教师培训评价详细
export function getJxzxpxpj(id) {
  return request({
    url: '/jxjs/jxzxpxpj/' + id,
    method: 'get'
  })
}

// 新增见习之星教师培训评价
export function addJxzxpxpj(data) {
  return request({
    url: '/jxjs/jxzxpxpj',
    method: 'post',
    data: data
  })
}

// 修改见习之星教师培训评价
export function updateJxzxpxpj(data) {
  return request({
    url: '/jxjs/jxzxpxpj',
    method: 'put',
    data: data
  })
}

// 删除见习之星教师培训评价
export function delJxzxpxpj(id) {
  return request({
    url: '/jxjs/jxzxpxpj/' + id,
    method: 'delete'
  })
}

// 导出见习之星教师培训评价
export function exportJxzxpxpj(query) {
  return request({
    url: '/jxjs/jxzxpxpj/export',
    method: 'get',
    params: query
  })
}