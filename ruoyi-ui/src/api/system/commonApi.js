import request from '@/utils/request'

// get请求封装
export function getReq(url, data) {
  return request({
    url: url,
    method: 'get',
    data: data
  })
}

// put请求封装
export function putReq(url, data) {
  return request({
    url: url,
    method: 'put',
    data: data
  })
}

// post请求封装
export function postReq(url, data) {
  return request({
    url: url,
    method: 'post',
    data: data
  })
}

// delete请求封装
export function deleteReq(url, data) {
  return request({
    url: url,
    method: 'delete',
    data: data
  })
}
