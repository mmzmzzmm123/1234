import request from '@/utils/request'

// 查询记账账本列表
export function listBook(query) {
  return request({
    url: '/fin/book/list',
    method: 'get',
    params: query
  })
}

// 查询记账账本详细
export function getBook(bookId) {
  return request({
    url: '/fin/book/' + bookId,
    method: 'get'
  })
}

// 新增记账账本
export function addBook(data) {
  return request({
    url: '/fin/book',
    method: 'post',
    data: data
  })
}

// 修改记账账本
export function updateBook(data) {
  return request({
    url: '/fin/book',
    method: 'put',
    data: data
  })
}

// 删除记账账本
export function delBook(bookId) {
  return request({
    url: '/fin/book/' + bookId,
    method: 'delete'
  })
}
