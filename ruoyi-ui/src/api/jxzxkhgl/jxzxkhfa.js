import request from '@/utils/request'

// 查询见习之星考核方案列表
export function listJxzxkhfa(query) {
  return request({
    url: '/jxzxkhgl/jxzxkhfa/list',
    method: 'get',
    params: query
  })
}

// 查询见习之星考核方案详细
export function getJxzxkhfa(id) {
  return request({
    url: '/jxzxkhgl/jxzxkhfa/' + id,
    method: 'get'
  })
}

// 新增见习之星考核方案
export function addJxzxkhfa(data) {
  return request({
    url: '/jxzxkhgl/jxzxkhfa',
    method: 'post',
    data: data
  })
}

// 修改见习之星考核方案
export function updateJxzxkhfa(data) {
  return request({
    url: '/jxzxkhgl/jxzxkhfa',
    method: 'put',
    data: data
  })
}

// 删除见习之星考核方案
export function delJxzxkhfa(id) {
  return request({
    url: '/jxzxkhgl/jxzxkhfa/' + id,
    method: 'delete'
  })
}

// 导出见习之星考核方案
export function exportJxzxkhfa(query) {
  return request({
    url: '/jxzxkhgl/jxzxkhfa/export',
    method: 'get',
    params: query
  })
}
