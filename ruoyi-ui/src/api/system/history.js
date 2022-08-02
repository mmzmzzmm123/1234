import request from '@/utils/request'

// 查询flyway管理列表
export function listHistory(query) {
  return request({
    url: '/system/history/list',
    method: 'get',
    params: query
  })
}

// 查询flyway管理详细
export function getHistory(installedRank) {
  return request({
    url: '/system/history/' + installedRank,
    method: 'get'
  })
}

// 新增flyway管理
export function addHistory(data) {
  return request({
    url: '/system/history',
    method: 'post',
    data: data
  })
}

// 修改flyway管理
export function updateHistory(data) {
  return request({
    url: '/system/history',
    method: 'put',
    data: data
  })
}

// 删除flyway管理
export function delHistory(installedRank) {
  return request({
    url: '/system/history/' + installedRank,
    method: 'delete'
  })
}
