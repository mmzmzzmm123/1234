import request from '@/utils/request'

// 查询菜地划分列表
export function listGarden(query) {
  return request({
    url: '/csa/garden/list',
    method: 'get',
    params: query
  })
}

// 按代码过滤可绑定的菜地列表
export function listCanSell(code) {
  return request({
    url: '/csa/garden/cansell/' + code,
    method: 'get'
  })
}

// 查询菜地划分详细
export function getGarden(id) {
  return request({
    url: '/csa/garden/' + id,
    method: 'get'
  })
}

// 新增菜地划分
export function addGarden(data) {
  return request({
    url: '/csa/garden',
    method: 'post',
    data: data
  })
}

// 修改菜地划分
export function updateGarden(data) {
  return request({
    url: '/csa/garden',
    method: 'put',
    data: data
  })
}

// 批量修改菜地划分状态
export function updateGardenStatus(data, id) {
  return request({
    url: '/csa/garden/' + id,
    method: 'put',
    data: data
  })
}


// 删除菜地划分
export function delGarden(id) {
  return request({
    url: '/csa/garden/' + id,
    method: 'delete'
  })
}
