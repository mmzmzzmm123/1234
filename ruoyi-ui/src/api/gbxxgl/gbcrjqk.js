import request from '@/utils/request'

// 查询干部出入境情况列表
export function listGbcrjqk(query) {
  return request({
    url: '/gbxxgl/gbcrjqk/list',
    method: 'get',
    params: query
  })
}

// 查询干部出入境情况详细
export function getGbcrjqk(id) {
  return request({
    url: '/gbxxgl/gbcrjqk/' + id,
    method: 'get'
  })
}

// 新增干部出入境情况
export function addGbcrjqk(data) {
  return request({
    url: '/gbxxgl/gbcrjqk',
    method: 'post',
    data: data
  })
}

// 修改干部出入境情况
export function updateGbcrjqk(data) {
  return request({
    url: '/gbxxgl/gbcrjqk',
    method: 'put',
    data: data
  })
}

// 删除干部出入境情况
export function delGbcrjqk(id) {
  return request({
    url: '/gbxxgl/gbcrjqk/' + id,
    method: 'delete'
  })
}

// 导出干部出入境情况
export function exportGbcrjqk(query) {
  return request({
    url: '/gbxxgl/gbcrjqk/export',
    method: 'get',
    params: query
  })
}