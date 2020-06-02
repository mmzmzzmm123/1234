import request from '@/utils/request'

// 查询链家小区列表
export function list(query) {
  return request({
    url: '/data/adrdict/lianjia/list',
    method: 'get',
    params: query
  })
}

// 查询链家小区详细
export function get(id) {
  return request({
    url: '/data/adrdict/lianjia/' + id,
    method: 'get'
  })
}

// 修改链家小区
export function update(data) {
  return request({
    url: '/data/adrdict/lianjia',
    method: 'put',
    data: data
  })
}

// 导出链家小区
export function export2File(query) {
  return request({
    url: '/data/adrdict/lianjia/export',
    method: 'get',
    params: query
  })
}
