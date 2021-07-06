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


// 根据客户唯一标识查询对应基础信息问卷调查表
export function getCustomerSurvey(customerKey) {
  return request({
    url: '/investigate/getCustomerSurvey/'+(customerKey != undefined && customerKey != null ? customerKey : ""),
    method: 'get'
  })
}

// 添加客户基础信息问卷，携带者客户唯一标识
export function addCustomerSurvey(customerSurvey) {
  return request({
    url: '/investigate/addCustomerSurvey',
    method: 'post',
    data: customerSurvey
  })
}


