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

// 新增客户健康
export function addCustomerHealthy(data) {
  return request({
    url: '/investigate/addCustomerHealthy',
    method: 'post',
    data: data
  })
}

// 根据加密客户ID获取对应客户信息
export function getCustomerBaseMessage(id) {
  return request({
    url: '/investigate/getCustomerBaseMessage/' + id,
    method: 'get'
  })
}

// 新增客户外食热量计算统计
export function addFoodHeatStatistics(data) {
  return request({
    url: '/investigate/addFoodHeatStatistics',
    method: 'post',
    data: data
  })
}

