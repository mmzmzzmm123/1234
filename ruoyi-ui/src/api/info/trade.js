import request from '@/utils/request'

// 查询交易信息列表
export function listInfo(query) {
  return request({
    url: '/info/trade/list',
    method: 'get',
    params: query
  })
}

// 查询交易信息详细
export function getInfo(id) {
  return request({
    url: '/info/trade/' + id,
    method: 'get'
  })
}

// 新增交易信息
export function addInfo(data) {
  return request({
    url: '/info/trade',
    method: 'post',
    data: data
  })
}

// 修改交易信息
export function updateInfo(data) {
  return request({
    url: '/info/trade',
    method: 'put',
    data: data
  })
}

// 删除交易信息
export function delInfo(id) {
  return request({
    url: '/info/trade/' + id,
    method: 'delete'
  })
}
