import request from '@/utils/request'

// 查询干部基本情况列表
export function listGbjbqk(query) {
  return request({
    url: '/gbxxgl/gbjbqk/list',
    method: 'get',
    params: query
  })
}

// 查询干部基本情况详细
export function getGbjbqk(id) {
  return request({
    url: '/gbxxgl/gbjbqk/' + id,
    method: 'get'
  })
}

// 新增干部基本情况
export function addGbjbqk(data) {
  return request({
    url: '/gbxxgl/gbjbqk',
    method: 'post',
    data: data
  })
}

// 修改干部基本情况
export function updateGbjbqk(data) {
  return request({
    url: '/gbxxgl/gbjbqk',
    method: 'put',
    data: data
  })
}

// 删除干部基本情况
export function delGbjbqk(id) {
  return request({
    url: '/gbxxgl/gbjbqk/' + id,
    method: 'delete'
  })
}

// 导出干部基本情况
export function exportGbjbqk(query) {
  return request({
    url: '/gbxxgl/gbjbqk/export',
    method: 'get',
    params: query
  })
}