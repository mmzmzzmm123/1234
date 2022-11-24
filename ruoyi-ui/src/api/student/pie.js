import request from '@/utils/request'

// 查询学生信息列表
export function acc() {
  return request({
    url: '/student/pie/acc',
    method: 'get',
  })
}
export function residence() {
    return request({
      url: '/student/pie/residence',
      method: 'get',
    })
}
export function nobackschool() {
    return request({
      url: '/student/pie/nobackschool',
      method: 'get',
    })
}
export function control() {
    return request({
      url: '/student/pie/control',
      method: 'get',
    })
}
export function placetoschool() {
    return request({
      url: '/student/pie/placetoschool',
      method: 'get',
    })
}