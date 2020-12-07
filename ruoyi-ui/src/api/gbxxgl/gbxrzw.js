import request from '@/utils/request'

// 查询干部任职情况-现任职务列表
export function listGbxrzw(query) {
  return request({
    url: '/gbxxgl/gbxrzw/list',
    method: 'get',
    params: query
  })
}

// 查询干部任职情况-现任职务详细
export function getGbxrzw(id) {
  return request({
    url: '/gbxxgl/gbxrzw/' + id,
    method: 'get'
  })
}

// 新增干部任职情况-现任职务
export function addGbxrzw(data) {
  return request({
    url: '/gbxxgl/gbxrzw',
    method: 'post',
    data: data
  })
}

// 修改干部任职情况-现任职务
export function updateGbxrzw(data) {
  return request({
    url: '/gbxxgl/gbxrzw',
    method: 'put',
    data: data
  })
}

// 删除干部任职情况-现任职务
export function delGbxrzw(id) {
  return request({
    url: '/gbxxgl/gbxrzw/' + id,
    method: 'delete'
  })
}

// 导出干部任职情况-现任职务
export function exportGbxrzw(query) {
  return request({
    url: '/gbxxgl/gbxrzw/export',
    method: 'get',
    params: query
  })
}