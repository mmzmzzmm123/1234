import request from '@/utils/request'

// 查询提成规则参数列表
export function listBonusParam(query) {
  return request({
    url: '/sysmanage/bonusParam/list',
    method: 'get',
    params: query
  })
}

// 查询提成规则参数详细
export function getBonusParam(id) {
  return request({
    url: '/sysmanage/bonusParam/' + id,
    method: 'get'
  })
}

// 新增提成规则参数
export function addBonusParam(data) {
  return request({
    url: '/sysmanage/bonusParam',
    method: 'post',
    data: data
  })
}

// 修改提成规则参数
export function updateBonusParam(data) {
  return request({
    url: '/sysmanage/bonusParam',
    method: 'put',
    data: data
  })
}

// 删除提成规则参数
export function delBonusParam(id) {
  return request({
    url: '/sysmanage/bonusParam/' + id,
    method: 'delete'
  })
}
