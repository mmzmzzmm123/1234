import request from '@/utils/request'

// 查询干部学习经历列表
export function listGbxxjl(query) {
  return request({
    url: '/gbxxgl/gbxxjl/list',
    method: 'get',
    params: query
  })
}

// 查询干部学习经历详细
export function getGbxxjl(id) {
  return request({
    url: '/gbxxgl/gbxxjl/' + id,
    method: 'get'
  })
}

// 新增干部学习经历
export function addGbxxjl(data) {
  return request({
    url: '/gbxxgl/gbxxjl',
    method: 'post',
    data: data
  })
}

// 修改干部学习经历
export function updateGbxxjl(data) {
  return request({
    url: '/gbxxgl/gbxxjl',
    method: 'put',
    data: data
  })
}

// 删除干部学习经历
export function delGbxxjl(id) {
  return request({
    url: '/gbxxgl/gbxxjl/' + id,
    method: 'delete'
  })
}

// 导出干部学习经历
export function exportGbxxjl(query) {
  return request({
    url: '/gbxxgl/gbxxjl/export',
    method: 'get',
    params: query
  })
}
