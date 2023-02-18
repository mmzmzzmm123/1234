import request from '@/utils/request'

// 查询竞赛基础信息列表
export function listContestInfo(query) {
  return request({
    url: '/contest/contestInfo/list',
    method: 'get',
    params: query
  })
}

// 查询竞赛基础信息详细
export function getContestInfo(contestId) {
  return request({
    url: '/contest/contestInfo/' + contestId,
    method: 'get'
  })
}

// 新增竞赛基础信息
export function addContestInfo(data) {
  return request({
    url: '/contest/contestInfo',
    method: 'post',
    data: data
  })
}

// 修改竞赛基础信息
export function updateContestInfo(data) {
  return request({
    url: '/contest/contestInfo',
    method: 'put',
    data: data
  })
}

// 删除竞赛基础信息
export function delContestInfo(contestId) {
  return request({
    url: '/contest/contestInfo/' + contestId,
    method: 'delete'
  })
}
