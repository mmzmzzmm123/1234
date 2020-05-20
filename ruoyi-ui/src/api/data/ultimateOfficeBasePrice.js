import request from '@/utils/request'

// 查询【请填写功能名称】列表
export function listUltimate(query) {
  return request({
    url: '/data/compute/price/office/list',
    method: 'get',
    params: query
  })
}

// 查询【请填写功能名称】详细
export function getUltimate(id) {
  return request({
    url: '/data/compute/price/office/' + id,
    method: 'get'
  })
}

// // 新增【请填写功能名称】
// export function addUltimate(data) {
//   return request({
//     url: '/data/compute/price/office',
//     method: 'post',
//     data: data
//   })
// }

// 修改【请填写功能名称】
export function updateUltimate(data) {
  return request({
    url: '/data/compute/price/office',
    method: 'put',
    data: data
  })
}

// // 删除【请填写功能名称】
// export function delUltimate(id) {
//   return request({
//     url: '/data/compute/price/office/' + id,
//     method: 'delete'
//   })
// }

// 导出【请填写功能名称】
export function exportUltimate(query) {
  return request({
    url: '/data/compute/price/office/export',
    method: 'get',
    params: query
  })
}
