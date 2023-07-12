import request from '@/utils/request'

// 查询咨询类型列表
export function getList(query) {
  return request({
    url: '/marketing/user/integral/list',
    method: 'get',
    params: query
  })
}
