import request from '@/utils/request'

// 查询获奖登记列表
export function listAward(query) {
  return request({
    url: '/contest/award/list',
    method: 'get',
    params: query
  })
}

// 查询获奖登记详细
export function getAward(awardId) {
  return request({
    url: '/contest/award/' + awardId,
    method: 'get'
  })
}

// 新增获奖登记
export function addAward(data) {
  return request({
    url: '/contest/award',
    method: 'post',
    data: data
  })
}

// 修改获奖登记
export function updateAward(data) {
  return request({
    url: '/contest/award',
    method: 'put',
    data: data
  })
}

// 删除获奖登记
export function delAward(awardId) {
  return request({
    url: '/contest/award/' + awardId,
    method: 'delete'
  })
}
