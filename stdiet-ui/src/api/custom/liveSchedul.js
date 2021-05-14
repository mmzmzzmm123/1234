import request from '@/utils/request'

// 查询直播排班列表
export function listLiveSchedul(query) {
  return request({
    url: '/custom/liveSchedul/list',
    method: 'get',
    params: query
  })
}

// 查询直播排班详细
export function getLiveSchedul(id) {
  return request({
    url: '/custom/liveSchedul/' + id,
    method: 'get'
  })
}

// 新增直播排班
export function addLiveSchedul(data) {
  return request({
    url: '/custom/liveSchedul',
    method: 'post',
    data: data
  })
}

// 修改直播排班
export function updateLiveSchedul(data) {
  return request({
    url: '/custom/liveSchedul',
    method: 'put',
    data: data
  })
}

// 删除直播排班
export function delLiveSchedul(id) {
  return request({
    url: '/custom/liveSchedul/' + id,
    method: 'delete'
  })
}

// 导出直播排班
export function exportLiveSchedul(query) {
  return request({
    url: '/custom/liveSchedul/export',
    method: 'get',
    params: query
  })
}


// 更新直播状态
export function updateLiveStatus(data) {
  return request({
    url: '/custom/liveSchedul/updateLiveStatus',
    method: 'get',
    params: data
  })
}

// 复制上次直播记录
export function copyLastTimeLiveSchedul() {
  return request({
    url: '/custom/liveSchedul/copyLastTimeLiveSchedul',
    method: 'get',
    params: {}
  })
}


