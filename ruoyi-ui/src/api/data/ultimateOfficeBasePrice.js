import request from '@/utils/request'

// 查询办公基价列表
export function list(query) {
  return request({
    url: '/data/compute/price/office/list',
    method: 'get',
    params: query
  })
}

// 查询办公基价详细
export function getById(yearMonth, id) {
  return request({
    url: '/data/compute/price/office/' + yearMonth + '/' + id,
    method: 'get'
  })
}

// 修改办公基价
export function update(data) {
  return request({
    url: '/data/compute/price/office',
    method: 'put',
    data: data
  })
}

// 导出办公基价
export function export2File(query) {
  return request({
    url: '/data/compute/price/office/export',
    method: 'get',
    params: query
  })
}

// 年月
export function getYearMonthList() {
  return request({
    url: '/data/compute/price/office/yearmonth',
    method: 'get'
  })
}
