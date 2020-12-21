import request from '@/utils/request'

// 查询办学成果(干部管理-评价与考核)列表
export function listGbbxcg(query) {
  return request({
    url: '/gbxxgl/gbbxcg/list',
    method: 'get',
    params: query
  })
}

// 查询办学成果(干部管理-评价与考核)详细
export function getGbbxcg(id) {
  return request({
    url: '/gbxxgl/gbbxcg/' + id,
    method: 'get'
  })
}

// 新增办学成果(干部管理-评价与考核)
export function addGbbxcg(data) {
  return request({
    url: '/gbxxgl/gbbxcg',
    method: 'post',
    data: data
  })
}

// 修改办学成果(干部管理-评价与考核)
export function updateGbbxcg(data) {
  return request({
    url: '/gbxxgl/gbbxcg',
    method: 'put',
    data: data
  })
}

// 删除办学成果(干部管理-评价与考核)
export function delGbbxcg(id) {
  return request({
    url: '/gbxxgl/gbbxcg/' + id,
    method: 'delete'
  })
}

// 导出办学成果(干部管理-评价与考核)
export function exportGbbxcg(query) {
  return request({
    url: '/gbxxgl/gbbxcg/export',
    method: 'get',
    params: query
  })
}