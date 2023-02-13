import request from '@/utils/request'

// 查询参赛人员信息列表
export function listAwardPerson(query) {
  return request({
    url: '/contest/awardPerson/list',
    method: 'get',
    params: query
  })
}

// 查询参赛人员信息详细
export function getAwardPerson(awardPersonId) {
  return request({
    url: '/contest/awardPerson/' + awardPersonId,
    method: 'get'
  })
}

// 新增参赛人员信息
export function addAwardPerson(data) {
  return request({
    url: '/contest/awardPerson',
    method: 'post',
    data: data
  })
}

// 修改参赛人员信息
export function updateAwardPerson(data) {
  return request({
    url: '/contest/awardPerson',
    method: 'put',
    data: data
  })
}

// 删除参赛人员信息
export function delAwardPerson(awardPersonId) {
  return request({
    url: '/contest/awardPerson/' + awardPersonId,
    method: 'delete'
  })
}
