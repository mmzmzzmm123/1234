import request from '@/utils/request'

// 查询群体教师评选审核过程列表
export function listQtjspxsh(query) {
  return request({
    url: '/qtjs/qtjspxsh/list',
    method: 'get',
    params: query
  })
}

// 查询群体教师评选审核过程详细
export function getQtjspxsh(id) {
  return request({
    url: '/qtjs/qtjspxsh/' + id,
    method: 'get'
  })
}


// 查询考核审核过程列表
export function getQtjspxshByFaid(query) {
    return request({
      url: '/qtjs/qtjspxsh/listbyfaid',
      method: 'get',
      params: query
    })
  }

// 新增群体教师评选审核过程
export function addQtjspxsh(data) {
  return request({
    url: '/qtjs/qtjspxsh',
    method: 'post',
    data: data
  })
}

// 修改群体教师评选审核过程
export function updateQtjspxsh(data) {
  return request({
    url: '/qtjs/qtjspxsh',
    method: 'put',
    data: data
  })
}

// 删除群体教师评选审核过程
export function delQtjspxsh(id) {
  return request({
    url: '/qtjs/qtjspxsh/' + id,
    method: 'delete'
  })
}

// 导出群体教师评选审核过程
export function exportQtjspxsh(query) {
  return request({
    url: '/qtjs/qtjspxsh/export',
    method: 'get',
    params: query
  })
}

// 提交考核审核过程
export function checkQtjspxsh(id) {
    return request({
      url: '/qtjs/qtjspxsh/check/' + id,
      method: 'post'
    })
  }
  
  // 删除考核审核过程
  export function backQtjspxsh(id, status) {
    return request({
      url: '/qtjs/qtjspxsh/back/' + id + '/' + status,
      method: 'post'
    })
  }