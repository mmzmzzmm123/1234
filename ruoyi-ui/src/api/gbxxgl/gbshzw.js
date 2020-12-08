import request from '@/utils/request'

// 查询干部任职情况-社会职务列表
export function listGbshzw(query) {
  return request({
    url: '/gbxxgl/gbshzw/list',
    method: 'get',
    params: query
  })
}

// 查询干部任职情况-社会职务详细
export function getGbshzw(id) {
  return request({
    url: '/gbxxgl/gbshzw/' + id,
    method: 'get'
  })
}

// 新增干部任职情况-社会职务
export function addGbshzw(data) {
  return request({
    url: '/gbxxgl/gbshzw',
    method: 'post',
    data: data
  })
}

// 修改干部任职情况-社会职务
export function updateGbshzw(data) {
  return request({
    url: '/gbxxgl/gbshzw',
    method: 'put',
    data: data
  })
}

// 删除干部任职情况-社会职务
export function delGbshzw(id) {
  return request({
    url: '/gbxxgl/gbshzw/' + id,
    method: 'delete'
  })
}

// 导出干部任职情况-社会职务
export function exportGbshzw(query) {
  return request({
    url: '/gbxxgl/gbshzw/export',
    method: 'get',
    params: query
  })
}