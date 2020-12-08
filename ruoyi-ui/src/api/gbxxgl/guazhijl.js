import request from '@/utils/request'

// 查询干部挂职经历列表
export function listGuazhijl(query) {
  return request({
    url: '/gbxxgl/guazhijl/list',
    method: 'get',
    params: query
  })
}

// 查询干部挂职经历详细
export function getGuazhijl(id) {
  return request({
    url: '/gbxxgl/guazhijl/' + id,
    method: 'get'
  })
}

// 新增干部挂职经历
export function addGuazhijl(data) {
  return request({
    url: '/gbxxgl/guazhijl',
    method: 'post',
    data: data
  })
}

// 修改干部挂职经历
export function updateGuazhijl(data) {
  return request({
    url: '/gbxxgl/guazhijl',
    method: 'put',
    data: data
  })
}

// 删除干部挂职经历
export function delGuazhijl(id) {
  return request({
    url: '/gbxxgl/guazhijl/' + id,
    method: 'delete'
  })
}

// 导出干部挂职经历
export function exportGuazhijl(query) {
  return request({
    url: '/gbxxgl/guazhijl/export',
    method: 'get',
    params: query
  })
}
