import request from '@/utils/request'

// 查询培训列表
export function listVideo(query) {
  return request({
    url: '/benyi/video/list',
    method: 'get',
    params: query
  })
}


// 查询培训列表
export function listScoreAndFree(id) {
  return request({
    url: '/benyi/video/listscoreandfree/'+id,
    method: 'get'
  })
}

// 查询培训详细
export function getVideo(id) {
  return request({
    url: '/benyi/video/' + id,
    method: 'get'
  })
}

// 新增培训
export function addVideo(data) {
  return request({
    url: '/benyi/video',
    method: 'post',
    data: data
  })
}

// 获取七牛云token
export function getQiNiuToken(data) {
  return request({
    url: '/common/getQiNiuToken',
    method: 'post',
    data: data
  })
}

// 修改培训
export function updateVideo(data) {
  return request({
    url: '/benyi/video',
    method: 'put',
    data: data
  })
}

// 删除培训
export function delVideo(id) {
  return request({
    url: '/benyi/video/' + id,
    method: 'delete'
  })
}

// 导出培训
export function exportVideo(query) {
  return request({
    url: '/benyi/video/export',
    method: 'get',
    params: query
  })
}