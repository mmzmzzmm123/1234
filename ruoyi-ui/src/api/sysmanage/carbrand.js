import request from '@/utils/request'

// 查询汽车品牌列表
export function listCarbrand(query) {
  return request({
    url: '/sysmanage/carbrand/list',
    method: 'get',
    params: query
  })
}

// 查询汽车品牌详细
export function getCarbrand(id) {
  return request({
    url: '/sysmanage/carbrand/' + id,
    method: 'get'
  })
}

// 新增汽车品牌
export function addCarbrand(data) {
  return request({
    url: '/sysmanage/carbrand',
    method: 'post',
    data: data
  })
}

// 修改汽车品牌
export function updateCarbrand(data) {
  return request({
    url: '/sysmanage/carbrand',
    method: 'put',
    data: data
  })
}

// 删除汽车品牌
export function delCarbrand(id) {
  return request({
    url: '/sysmanage/carbrand/' + id,
    method: 'delete'
  })
}
