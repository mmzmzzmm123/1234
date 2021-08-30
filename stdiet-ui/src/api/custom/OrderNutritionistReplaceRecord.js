import request from '@/utils/request'

// 查询订单营养师、售后更换记录列表
export function listOrderNutritionistReplaceRecord(query) {
  return request({
    url: '/custom/orderNutritionistReplaceRecord/list',
    method: 'get',
    params: query
  })
}

// 查询订单营养师、售后更换记录详细
export function getOrderNutritionistReplaceRecord(id) {
  return request({
    url: '/custom/orderNutritionistReplaceRecord/' + id,
    method: 'get'
  })
}

// 新增订单营养师、售后更换记录
export function addOrderNutritionistReplaceRecord(data) {
  return request({
    url: '/custom/orderNutritionistReplaceRecord',
    method: 'post',
    data: data
  })
}

// 修改订单营养师、售后更换记录
export function updateOrderNutritionistReplaceRecord(data) {
  return request({
    url: '/custom/orderNutritionistReplaceRecord',
    method: 'put',
    data: data
  })
}

// 删除订单营养师、售后更换记录
export function delOrderNutritionistReplaceRecord(id) {
  return request({
    url: '/custom/orderNutritionistReplaceRecord/' + id,
    method: 'delete'
  })
}

// 导出订单营养师、售后更换记录
export function exportOrderNutritionistReplaceRecord(query) {
  return request({
    url: '/custom/orderNutritionistReplaceRecord/export',
    method: 'get',
    params: query
  })
}

//批量导入更换记录
export function addMuchReplaceRecord(data){
  return request({
    url: '/custom/orderNutritionistReplaceRecord/addMuchReplaceRecord',
    method: 'post',
    params: data
  })
}

// 下载导入模板
export function downImportTemplate() {
  return request({
    url: '/custom/orderNutritionistReplaceRecord/downAddMuchReplaceRecordTemplate',
    method: 'get'
  })
}
