import request from '@/utils/request'

// 查询业务考核(干部管理-评价与考核)列表
export function listGbywkh(query) {
  return request({
    url: '/gbxxgl/gbywkh/list',
    method: 'get',
    params: query
  })
}

// 查询业务考核(干部管理-评价与考核)详细
export function getGbywkh(id) {
  return request({
    url: '/gbxxgl/gbywkh/' + id,
    method: 'get'
  })
}

// 新增业务考核(干部管理-评价与考核)
export function addGbywkh(data) {
  return request({
    url: '/gbxxgl/gbywkh',
    method: 'post',
    data: data
  })
}

// 修改业务考核(干部管理-评价与考核)
export function updateGbywkh(data) {
  return request({
    url: '/gbxxgl/gbywkh',
    method: 'put',
    data: data
  })
}

// 删除业务考核(干部管理-评价与考核)
export function delGbywkh(id) {
  return request({
    url: '/gbxxgl/gbywkh/' + id,
    method: 'delete'
  })
}

// 导出业务考核(干部管理-评价与考核)
export function exportGbywkh(query) {
  return request({
    url: '/gbxxgl/gbywkh/export',
    method: 'get',
    params: query
  })
}