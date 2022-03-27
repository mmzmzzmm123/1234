import request from '@/utils/request'

// 查询会员卡列表
export function listCard(query) {
  return request({
    url: '/csa/card/list',
    method: 'get',
    params: query
  })
}

// 查询会员卡详细
export function getCard(id) {
  return request({
    url: '/csa/card/' + id,
    method: 'get'
  })
}

// 新增会员卡
export function addCard(data) {
  return request({
    url: '/csa/card',
    method: 'post',
    data: data
  })
}

// 修改会员卡
export function updateCard(data) {
  return request({
    url: '/csa/card',
    method: 'put',
    data: data
  })
}

// 删除会员卡
export function delCard(id) {
  return request({
    url: '/csa/card/' + id,
    method: 'delete'
  })
}
