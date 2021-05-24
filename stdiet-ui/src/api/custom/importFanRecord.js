import request from '@/utils/request'

// 查询导粉管理列表
export function listImportFanRecord(query) {
  return request({
    url: '/custom/importFanRecord/list',
    method: 'get',
    params: query
  })
}

// 查询导粉管理详细
export function getImportFanRecord(id) {
  return request({
    url: '/custom/importFanRecord/' + id,
    method: 'get'
  })
}

// 新增导粉管理
export function addImportFanRecord(data) {
  return request({
    url: '/custom/importFanRecord',
    method: 'post',
    data: data
  })
}

// 修改导粉管理
export function updateImportFanRecord(data) {
  return request({
    url: '/custom/importFanRecord',
    method: 'put',
    data: data
  })
}

// 删除导粉管理
export function delImportFanRecord(id) {
  return request({
    url: '/custom/importFanRecord/' + id,
    method: 'delete'
  })
}

// 导出导粉管理
export function exportImportFanRecord(query) {
  return request({
    url: '/custom/importFanRecord/export',
    method: 'get',
    params: query
  })
}

export function getWxAccountAndSale(query){
    return request({
        url: '/custom/importFanRecord/getWxAccountAndSale',
        method: 'get',
        params: query
      })
}

// 删除导粉记录中对应的微信记录
export function removeFanWxAccount(id) {
  return request({
    url: '/custom/importFanRecord/removeFanWxAccount/' + id,
    method: 'get'
  })
}


export function saveWxAccountFanNum(data){
  return request({
    url: '/custom/importFanRecord/saveWxAccountFanNum',
    method: 'get',
    params: data
  })
}
