import request from '@/utils/request'

// 查询干部出入境证件情况列表
export function listGbcrjzjqk(query) {
  return request({
    url: '/gbxxgl/gbcrjzjqk/list',
    method: 'get',
    params: query
  })
}

// 查询干部出入境证件情况详细
export function getGbcrjzjqk(id) {
  return request({
    url: '/gbxxgl/gbcrjzjqk/' + id,
    method: 'get'
  })
}

// 新增干部出入境证件情况
export function addGbcrjzjqk(data) {
  return request({
    url: '/gbxxgl/gbcrjzjqk',
    method: 'post',
    data: data
  })
}

// 修改干部出入境证件情况
export function updateGbcrjzjqk(data) {
  return request({
    url: '/gbxxgl/gbcrjzjqk',
    method: 'put',
    data: data
  })
}

// 删除干部出入境证件情况
export function delGbcrjzjqk(id) {
  return request({
    url: '/gbxxgl/gbcrjzjqk/' + id,
    method: 'delete'
  })
}

// 导出干部出入境证件情况
export function exportGbcrjzjqk(query) {
  return request({
    url: '/gbxxgl/gbcrjzjqk/export',
    method: 'get',
    params: query
  })
}