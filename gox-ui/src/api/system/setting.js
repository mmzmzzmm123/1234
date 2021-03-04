import request from '@/utils/request'

// 查询档号设置列表
export function listSetting(query) {
  return request({
    url: '/system/setting/list',
    method: 'get',
    params: query
  })
}

// 查询档号设置详细
export function getSetting(nodeId,deptId) {
  return request({
    url: '/system/setting/' + nodeId+'/'+deptId,
    method: 'get'
  })
}

// 新增档号设置
export function addSetting(data) {
  return request({
    url: '/system/setting',
    method: 'post',
    data: data
  })
}

// 修改档号设置
export function updateSetting(data) {
  return request({
    url: '/system/setting',
    method: 'put',
    data: data
  })
}

// 删除档号设置
export function delSetting(id) {
  return request({
    url: '/system/setting/' + id,
    method: 'delete'
  })
}

// 导出档号设置
export function exportSetting(query) {
  return request({
    url: '/system/setting/export',
    method: 'get',
    params: query
  })
}
