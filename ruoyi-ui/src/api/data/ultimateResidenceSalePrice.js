import request from '@/utils/request'

// 查询住宅销售基价修正列表
export function list(query) {
  return request({
    url: '/data/sale-price/residence/ultimate/list',
    method: 'get',
    params: query
  })
}

// 查询住宅销售基价修正详细
export function get(id) {
  return request({
    url: '/data/sale-price/residence/ultimate/' + id,
    method: 'get'
  })
}

// 修改住宅销售基价修正
export function update(data) {
  return request({
    url: '/data/sale-price/residence/ultimate',
    method: 'put',
    data: data
  })
}

// 导出住宅销售基价修正
export function export2File(query) {
  return request({
    url: '/data/sale-price/residence/ultimate/export',
    method: 'get',
    params: query
  })
}

export function getYearMonthList() {
  return request({
    url: '/data/sale-price/residence/ultimate/yearmonth',
    method: 'get'
  })
}
