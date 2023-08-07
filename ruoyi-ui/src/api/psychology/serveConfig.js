import request from '@/utils/request'

// 查询咨询服务配置列表
export function listServeConfig(query) {
  return request({
    url: '/psychology/serveConfig/list',
    method: 'get',
    params: query
  })
}

// 查询咨询服务配置详细
export function getServeConfig(id) {
  return request({
    url: '/psychology/serveConfig/' + id,
    method: 'get'
  })
}

export function getServeRef(query) {
  return request({
    url: '/psychology/serveConfig/getServeRef',
    method: 'get',
    params: query
  })
}

export function getConsultServeRef() {
  return request({
    url: '/psychology/serveConfig/getConsultServeRef',
    method: 'get'
  })
}

export function delConsultServeRef(query) {
  return request({
    url: '/psychology/serveConfig/delConsultServeRef',
    method: 'post',
    data: query
  })
}

// 新增咨询服务配置
export function addServeConfig(data) {
  return request({
    url: '/psychology/serveConfig',
    method: 'post',
    data: data
  })
}

// 修改咨询服务配置
export function updateServeConfig(data) {
  return request({
    url: '/psychology/serveConfig',
    method: 'put',
    data: data
  })
}

// 删除咨询服务配置
export function delServeConfig(id) {
  return request({
    url: '/psychology/serveConfig/' + id,
    method: 'delete'
  })
}
