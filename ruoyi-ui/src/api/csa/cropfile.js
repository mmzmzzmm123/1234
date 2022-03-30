import request from '@/utils/request'

// 查询作物档案列表
export function listCropfile(query) {
  return request({
    url: '/csa/cropfile/list',
    method: 'get',
    params: query
  })
}

// 查询作物档案详细
export function getCropfile(cropId) {
  return request({
    url: '/csa/cropfile/' + cropId,
    method: 'get'
  })
}

// 新增作物档案
export function addCropfile(data) {
  return request({
    url: '/csa/cropfile',
    method: 'post',
    data: data
  })
}

// 修改作物档案
export function updateCropfile(data) {
  return request({
    url: '/csa/cropfile',
    method: 'put',
    data: data
  })
}

// 删除作物档案
export function delCropfile(cropId) {
  return request({
    url: '/csa/cropfile/' + cropId,
    method: 'delete'
  })
}
