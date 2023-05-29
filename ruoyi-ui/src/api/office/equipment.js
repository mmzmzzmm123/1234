import request from '@/utils/request'

// 查询设备列表列表
export function listEquipment(query) {
  return request({
    url: '/office/equipment/list',
    method: 'get',
    params: query
  })
}

// 查询设备列表详细
export function getEquipment(id) {
  return request({
    url: '/office/equipment/' + id,
    method: 'get'
  })
}

// 新增设备列表
export function addEquipment(data) {
  return request({
    url: '/office/equipment',
    method: 'post',
    data: data
  })
}

// 修改设备列表
export function updateEquipment(data) {
  return request({
    url: '/office/equipment',
    method: 'put',
    data: data
  })
}

// 删除设备列表
export function delEquipment(id) {
  return request({
    url: '/office/equipment/' + id,
    method: 'delete'
  })
}
