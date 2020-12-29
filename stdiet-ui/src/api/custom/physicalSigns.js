import request from '@/utils/request'

// 查询体征列表
export function listPhysicalSigns(query) {
  return request({
    url: '/custom/physicalSigns/list',
    method: 'get',
    params: query
  })
}

// 查询体征详细
export function getPhysicalSigns(id) {
  return request({
    url: '/custom/physicalSigns/' + id,
    method: 'get'
  })
}

// 新增体征
export function addPhysicalSigns(data) {
  return request({
    url: '/custom/physicalSigns',
    method: 'post',
    data: data
  })
}

// 修改体征
export function updatePhysicalSigns(data) {
  return request({
    url: '/custom/physicalSigns',
    method: 'put',
    data: data
  })
}

// 删除体征
export function delPhysicalSigns(id) {
  return request({
    url: '/custom/physicalSigns/' + id,
    method: 'delete'
  })
}

// 导出体征
export function exportPhysicalSigns(query) {
  return request({
    url: '/custom/physicalSigns/export',
    method: 'get',
    params: query
  })
}
