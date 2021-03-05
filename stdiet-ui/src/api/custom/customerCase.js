import request from '@/utils/request'

// 查询客户案例管理列表
export function listCustomerCase(query) {
  return request({
    url: '/custom/customerCase/list',
    method: 'get',
    params: query
  })
}

// 查询客户案例管理详细
export function getCustomerCase(id) {
  return request({
    url: '/custom/customerCase/' + id,
    method: 'get'
  })
}

// 新增客户案例管理
export function addCustomerCase(data) {
  return request({
    url: '/custom/customerCase',
    method: 'post',
    data: data
  })
}

// 修改客户案例管理
export function updateCustomerCase(data) {
  return request({
    url: '/custom/customerCase',
    method: 'put',
    data: data
  })
}

// 删除客户案例管理
export function delCustomerCase(id) {
  return request({
    url: '/custom/customerCase/' + id,
    method: 'delete'
  })
}

// 导出客户案例管理
export function exportCustomerCase(query) {
  return request({
    url: '/custom/customerCase/export',
    method: 'get',
    params: query
  })
}

// 根据案例ID查询对应文件列表
export function getFileListByCaseId(caseId) {
  return request({
    url: '/custom/customerCase/getFileListByCaseId',
    method: 'get',
    params: {"caseId": caseId}
  })
}

// 上传案例文件
export function uploadCseFile(data) {
  return request({
    url: '/custom/customerCase/uploadCseFile',
    method: 'post',
    data: data
  })
}

// 下载案例文件
export function downCaseFile(downUrl) {
  window.location.href = downUrl;
}




