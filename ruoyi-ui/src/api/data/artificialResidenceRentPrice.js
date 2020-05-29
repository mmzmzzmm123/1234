import request from '@/utils/request'

// 查询人工住宅租赁基价列表
export function list(query) {
  return request({
    url: '/data/rentprice/residence/artificial/list',
    method: 'get',
    params: query
  })
}
