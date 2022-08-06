import request from '@/utils/request'

// 查询作业安排列表
export function listWord(query) {
  return request({
    url: '/work/word/list',
    method: 'get',
    params: query
  })
}

// 查询作业安排详细
export function getWord(id) {
  return request({
    url: '/work/word/' + id,
    method: 'get'
  })
}

// 新增作业安排
export function addWord(data) {
  return request({
    url: '/work/word',
    method: 'post',
    data: data
  })
}

// 修改作业安排
export function updateWord(data) {
  return request({
    url: '/work/word',
    method: 'put',
    data: data
  })
}

// 删除作业安排
export function delWord(id) {
  return request({
    url: '/work/word/' + id,
    method: 'delete'
  })
}
