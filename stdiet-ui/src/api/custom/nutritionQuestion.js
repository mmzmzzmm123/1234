import request from '@/utils/request'

// 查询营养知识小问答列表
export function listNutritionQuestion(query) {
  return request({
    url: '/custom/nutritionQuestion/list',
    method: 'get',
    params: query
  })
}

// 查询营养知识小问答详细
export function getNutritionQuestion(id) {
  return request({
    url: '/custom/nutritionQuestion/' + id,
    method: 'get'
  })
}

// 新增营养知识小问答
export function addNutritionQuestion(data) {
  return request({
    url: '/custom/nutritionQuestion',
    method: 'post',
    data: data
  })
}

// 修改营养知识小问答
export function updateNutritionQuestion(data) {
  return request({
    url: '/custom/nutritionQuestion',
    method: 'put',
    data: data
  })
}

// 删除营养知识小问答
export function delNutritionQuestion(id) {
  return request({
    url: '/custom/nutritionQuestion/' + id,
    method: 'delete'
  })
}

// 导出营养知识小问答
export function exportNutritionQuestion(query) {
  return request({
    url: '/custom/nutritionQuestion/export',
    method: 'get',
    params: query
  })
}

//更新营养知识小问答的展示状态
export function updateWxShow(data){
  return request({
    url: '/custom/nutritionQuestion/updateWxShow',
    method: 'get',
    params: data
  })
}