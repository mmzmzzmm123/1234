import request from '@/utils/request'

// 查询干部培训经历列表
export function listGbpxjl(query) {
  return request({
    url: '/gbxxgl/gbpxjl/list',
    method: 'get',
    params: query
  })
}

// 查询干部培训经历详细
export function getGbpxjl(id) {
  return request({
    url: '/gbxxgl/gbpxjl/' + id,
    method: 'get'
  })
}

// 新增干部培训经历
export function addGbpxjl(data) {
  return request({
    url: '/gbxxgl/gbpxjl',
    method: 'post',
    data: data
  })
}

// 修改干部培训经历
export function updateGbpxjl(data) {
  return request({
    url: '/gbxxgl/gbpxjl',
    method: 'put',
    data: data
  })
}

// 删除干部培训经历
export function delGbpxjl(id) {
  return request({
    url: '/gbxxgl/gbpxjl/' + id,
    method: 'delete'
  })
}

// 导出干部培训经历
export function exportGbpxjl(query) {
  return request({
    url: '/gbxxgl/gbpxjl/export',
    method: 'get',
    params: query
  })
}
