import request from '@/utils/request'

// 查询年度考核(干部管理-评价与考核)列表
export function listGbndkh(query) {
  return request({
    url: '/gbxxgl/gbndkh/list',
    method: 'get',
    params: query
  })
}

// 查询年度考核(干部管理-评价与考核)详细
export function getGbndkh(id) {
  return request({
    url: '/gbxxgl/gbndkh/' + id,
    method: 'get'
  })
}

// 新增年度考核(干部管理-评价与考核)
export function addGbndkh(data) {
  return request({
    url: '/gbxxgl/gbndkh',
    method: 'post',
    data: data
  })
}

// 修改年度考核(干部管理-评价与考核)
export function updateGbndkh(data) {
  return request({
    url: '/gbxxgl/gbndkh',
    method: 'put',
    data: data
  })
}

// 删除年度考核(干部管理-评价与考核)
export function delGbndkh(id) {
  return request({
    url: '/gbxxgl/gbndkh/' + id,
    method: 'delete'
  })
}

// 导出年度考核(干部管理-评价与考核)
export function exportGbndkh(query) {
  return request({
    url: '/gbxxgl/gbndkh/export',
    method: 'get',
    params: query
  })
}
