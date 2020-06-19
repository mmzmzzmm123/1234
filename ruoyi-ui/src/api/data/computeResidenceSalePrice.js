import request from '@/utils/request'

// 查询住宅销售基价列表
export function list(query) {
  return request({
    url: '/data/price/residence/sale/compute/list',
    method: 'get',
    params: query
  })
}

// 查询住宅销售基价详细
export function get(id) {
  return request({
    url: '/data/price/residence/sale/compute/' + id,
    method: 'get'
  })
}

// 修改住宅销售基价
export function update(data) {
  return request({
    url: '/data/price/residence/sale/compute',
    method: 'put',
    data: data
  })
}

// 导出住宅销售基价
export function export2File(query) {
  return request({
    url: '/data/price/residence/sale/compute/export',
    method: 'get',
    params: query
  })
}

export function getYearMonthList() {
  return request({
    url: '/data/price/residence/sale/compute/yearmonth',
    method: 'get'
  })
}
