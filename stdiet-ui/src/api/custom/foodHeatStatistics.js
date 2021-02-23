import request from '@/utils/request'

// 查询外食热量统计列表
export function listFoodHeatStatistics(query) {
  return request({
    url: '/custom/foodHeatStatistics/list',
    method: 'get',
    params: query
  })
}

// 查询外食热量统计详细
export function getFoodHeatStatistics(id) {
  return request({
    url: '/custom/foodHeatStatistics/' + id,
    method: 'get'
  })
}

// 新增外食热量统计
export function addFoodHeatStatistics(data) {
  return request({
    url: '/custom/foodHeatStatistics',
    method: 'post',
    data: data
  })
}

// 修改外食热量统计
export function updateFoodHeatStatistics(data) {
  return request({
    url: '/custom/foodHeatStatistics',
    method: 'put',
    data: data
  })
}

// 删除外食热量统计
export function delFoodHeatStatistics(id) {
  return request({
    url: '/custom/foodHeatStatistics/' + id,
    method: 'delete'
  })
}

// 导出外食热量统计
export function exportFoodHeatStatistics(query) {
  return request({
    url: '/custom/foodHeatStatistics/export',
    method: 'get',
    params: query
  })
}

// 新增外食热量统计
export function addFoodHeatData(data) {
  return request({
    url: '/custom/foodHeatStatistics/addFoodHeatData',
    method: 'post',
    data: data
  })
}

// 获取热量比例分析详情
export function getNutritionalCalories(id) {
  return request({
    url: '/custom/foodHeatStatistics/getNutritionalCalories',
    method: 'get',
    params: {"id": id}
  })
}



