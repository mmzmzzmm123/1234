import request from '@/utils/request'

// 查询【请填写功能名称】列表
export function listNewpeopleRation(query) {
  return request({
    url: '/system/newpeopleRation/list',
    method: 'get',
    params: query
  })
}

// 查询【请填写功能名称】详细
export function getNewpeopleRation(mId) {
  return request({
    url: '/system/newpeopleRation/' + mId,
    method: 'get'
  })
}

// 新增【请填写功能名称】
export function addNewpeopleRation(data) {
  return request({
    url: '/system/newpeopleRation',
    method: 'post',
    data: data
  })
}

// 修改【请填写功能名称】
export function updateNewpeopleRation(data) {
  return request({
    url: '/system/newpeopleRation',
    method: 'put',
    data: data
  })
}

// 删除【请填写功能名称】
export function delNewpeopleRation(mId) {
  return request({
    url: '/system/newpeopleRation/' + mId,
    method: 'delete'
  })
}
