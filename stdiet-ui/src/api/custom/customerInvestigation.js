import request from '@/utils/request'

// 调查页面查询体征列表
export function physicalSignsList(query) {
  return request({
    url: '/investigate/physicalSignsList',
    method: 'get',
    params: query
  })
}

// 调查页面新增客户资料
export function addCustomer(data) {
  return request({
    url: '/investigate/customerInvestigate',
    method: 'post',
    data: data
  })
}

// 根据字典类型查询字典数据信息
export function getDictData(dictType) {
  return request({
    url: '/investigate/type/' + dictType,
    method: 'get'
  })
}
