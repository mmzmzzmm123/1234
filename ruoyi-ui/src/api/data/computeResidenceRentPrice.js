import request from '@/utils/request'

// 查询计算住宅租赁基价列表
export function list(query) {
  return request({
    url: '/data/rent-price/residence/compute/list',
    method: 'get',
    params: query
  })
}

// 查询计算住宅租赁基价详细
export function get(id) {
  return request({
    url: '/data/rent-price/residence/compute/' + id,
    method: 'get'
  })
}

// 修改计算住宅租赁基价
export function update(data) {
  return request({
    url: '/data/rent-price/residence/compute',
    method: 'put',
    data: data
  })
}

// 导出计算住宅租赁基价
export function export2File(query) {
  return request({
    url: '/data/rent-price/residence/compute/export',
    method: 'get',
    params: query
  })
}

export function getYearMonthList() {
  return request({
    url: '/data/rent-price/residence/compute/yearmonth',
    method: 'get'
  })
}
