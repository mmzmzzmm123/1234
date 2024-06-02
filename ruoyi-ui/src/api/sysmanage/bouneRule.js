import request from '@/utils/request'

// 查询提成规则列表
export function listBouneRule(query) {
  return request({
    url: '/sysmanage/bouneRule/list',
    method: 'get',
    params: query
  })
}

// 查询提成规则详细
export function getBouneRule(id) {
  return request({
    url: '/sysmanage/bouneRule/' + id,
    method: 'get'
  })
}

// 新增提成规则
export function addBouneRule(data) {
  return request({
    url: '/sysmanage/bouneRule',
    method: 'post',
    data: data
  })
}

// 修改提成规则
export function updateBouneRule(data) {
  return request({
    url: '/sysmanage/bouneRule',
    method: 'put',
    data: data
  })
}

// 删除提成规则
export function delBouneRule(id) {
  return request({
    url: '/sysmanage/bouneRule/' + id,
    method: 'delete'
  })
}
