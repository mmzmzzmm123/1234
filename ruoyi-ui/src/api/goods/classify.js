import request from '@/utils/request'

// 查询货品分类列表
export function listClassify(query) {
  return request({
    url: '/goods/classify/list',
    method: 'get',
    params: query
  })
}

// 查询货品分类详细
export function getClassify(classifyId) {
  return request({
    url: '/goods/classify/' + classifyId,
    method: 'get'
  })
}

// 新增货品分类
export function addClassify(data) {
  return request({
    url: '/goods/classify',
    method: 'post',
    data: data
  })
}

// 修改货品分类
export function updateClassify(data) {
  return request({
    url: '/goods/classify',
    method: 'put',
    data: data
  })
}

// 删除货品分类
export function delClassify(classifyId) {
  return request({
    url: '/goods/classify/' + classifyId,
    method: 'delete'
  })
}

// 查询分类列表（排除节点）
export function listClassifyExcludeChild(classifyId) {
  return request({
    url: '/goods/classify/list/exclude/' + classifyId,
    method: 'get'
  })
}
