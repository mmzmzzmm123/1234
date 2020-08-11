import request from '@/utils/request'

// 查询住宅销售基价修正列表
export function list(query) {
  return request({
    url: '/data/cases/residence/aggregate-rent-case/list',
    method: 'get',
    params: query
  })
}

// 导出住宅销售基价修正
export function export2File(query) {
  return request({
    url: '/data/cases/residence/aggregate-rent-case/export',
    method: 'get',
    params: query
  })
}

export function getYearMonthList() {
  return request({
    url: '/data/cases/residence/aggregate-rent-case/yearmonth',
    method: 'get'
  })
}
