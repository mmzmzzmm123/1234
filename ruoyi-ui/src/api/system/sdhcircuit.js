import request from '@/utils/request'

// 查询【请填写功能名称】列表
export function listSdhcircuit(query) {
  return request({
    url: '/system/sdhcircuit/list',
    method: 'get',
    params: query
  })
}

export function listCity(query) {
  return request({
    url: '/system/listCity/list',
    method: 'get',
    params: query
  })
}

// 查询【请填写功能名称】详细
export function getSdhcircuit(id) {
  return request({
    url: '/system/sdhcircuit/' + id,
    method: 'get'
  })
}

// 新增【请填写功能名称】
export function addSdhcircuit(data) {
  return request({
    url: '/system/sdhcircuit',
    method: 'post',
    data: data
  })
}

// 修改【请填写功能名称】
export function updateSdhcircuit(data) {
  return request({
    url: '/system/sdhcircuit',
    method: 'put',
    data: data
  })
}

// 删除【请填写功能名称】
export function delSdhcircuit(id) {
  return request({
    url: '/system/sdhcircuit/' + id,
    method: 'delete'
  })
}
