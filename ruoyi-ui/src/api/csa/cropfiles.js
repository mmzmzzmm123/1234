import request from '@/utils/request'

// 查询作物档案列表
export function listCropfiles(query) {
  return request({
    url: '/csa/cropfiles/list',
    method: 'get',
    params: query
  })
}

// 查询作物档案详细
export function getCropfiles(id) {
  return request({
    url: '/csa/cropfiles/' + id,
    method: 'get'
  })
}

// 新增作物档案
export function addCropfiles(data) {
  return request({
    url: '/csa/cropfiles',
    method: 'post',
    data: data
  })
}

// 修改作物档案
export function updateCropfiles(data) {
  return request({
    url: '/csa/cropfiles',
    method: 'put',
    data: data
  })
}

// 删除作物档案
export function delCropfiles(id) {
  return request({
    url: '/csa/cropfiles/' + id,
    method: 'delete'
  })
}
