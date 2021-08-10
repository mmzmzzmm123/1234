import request from '@/utils/request'

// 查询快乐+1列表
export function listHappy(query) {
  return request({
    url: '/system/happy/list',
    method: 'get',
    params: query
  })
}

// 查询快乐+1详细
export function getHappy(happyId) {
  return request({
    url: '/system/happy/' + happyId,
    method: 'get'
  })
}

// 新增快乐+1
export function addHappy(data) {
  return request({
    url: '/system/happy',
    method: 'post',
    data: data
  })
}

// 修改快乐+1
export function updateHappy(data) {
  return request({
    url: '/system/happy',
    method: 'put',
    data: data
  })
}

// 删除快乐+1
export function delHappy(happyId) {
  return request({
    url: '/system/happy/' + happyId,
    method: 'delete'
  })
}

// 导出快乐+1
export function exportHappy(query) {
  return request({
    url: '/system/happy/export',
    method: 'get',
    params: query
  })
}