import request from '@/utils/request'

// 查询干部工作经历列表
export function listGbgzjl(query) {
  return request({
    url: '/gbxxgl/gbgzjl/list',
    method: 'get',
    params: query
  })
}

// 查询干部工作经历详细
export function getGbgzjl(id) {
  return request({
    url: '/gbxxgl/gbgzjl/' + id,
    method: 'get'
  })
}

// 新增干部工作经历
export function addGbgzjl(data) {
  return request({
    url: '/gbxxgl/gbgzjl',
    method: 'post',
    data: data
  })
}

// 修改干部工作经历
export function updateGbgzjl(data) {
  return request({
    url: '/gbxxgl/gbgzjl',
    method: 'put',
    data: data
  })
}

// 删除干部工作经历
export function delGbgzjl(id) {
  return request({
    url: '/gbxxgl/gbgzjl/' + id,
    method: 'delete'
  })
}

// 导出干部工作经历
export function exportGbgzjl(query) {
  return request({
    url: '/gbxxgl/gbgzjl/export',
    method: 'get',
    params: query
  })
}
