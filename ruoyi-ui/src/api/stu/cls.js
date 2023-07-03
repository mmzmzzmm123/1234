import request from '@/utils/request'

//查询所有班级
export function clsList(query) {
  return request({
    url: '/stu/base/listAll',
    method: 'get',
    data: query
  })
}
