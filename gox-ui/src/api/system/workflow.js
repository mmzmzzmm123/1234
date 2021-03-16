import request from '@/utils/request'

// 查询  列表
export function listNode(query) {
  return request({
    url: '/basic/node/list',
    method: 'get',
    params: query
  })
}
// 查询 工作流列表
export function listWork(query) {
  return request({
    url: '/basic/work/list',
    method: 'get',
    params: query
  })
}


// 查询  详细
export function getNode(id) {
  return request({
    url: '/basic/node/' + id,
    method: 'get'
  })
}

// 新增  
export function addNode(data) {
  return request({
    url: '/basic/node',
    method: 'post',
    data: data
  })
}

// 修改  
export function updateNode(data) {
  return request({
    url: '/basic/node',
    method: 'put',
    data: data
  })
}

// 删除  
export function delNode(id) {
  return request({
    url: '/basic/node/' + id,
    method: 'delete'
  })
}

// 导出  
export function exportNode(query) {
  return request({
    url: '/basic/node/export',
    method: 'get',
    params: query
  })
}