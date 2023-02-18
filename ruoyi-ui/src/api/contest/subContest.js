import request from '@/utils/request'

// 查询子竞赛信息列表
export function listSubContest(query) {
  return request({
    url: '/contest/subContest/list',
    method: 'get',
    params: query
  })
}

// 查询子竞赛信息列表
export function listWithParentContest(query) {
  return request({
    url: '/contest/subContest/listWithParentContest',
    method: 'get',
    params: query
  })
}

// 查询子竞赛信息详细
export function getSubContest(subContestId) {
  return request({
    url: '/contest/subContest/' + subContestId,
    method: 'get'
  })
}

// 新增子竞赛信息
export function addSubContest(data) {
  return request({
    url: '/contest/subContest',
    method: 'post',
    data: data
  })
}

// 修改子竞赛信息
export function updateSubContest(data) {
  return request({
    url: '/contest/subContest',
    method: 'put',
    data: data
  })
}

// 删除子竞赛信息
export function delSubContest(subContestId) {
  return request({
    url: '/contest/subContest/' + subContestId,
    method: 'delete'
  })
}
