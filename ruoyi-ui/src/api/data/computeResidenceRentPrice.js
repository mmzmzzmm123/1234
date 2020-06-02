import request from '@/utils/request'

// 查询【请填写功能名称】列表
export function list(query) {
  return request({
    url: '/data/rentprice/residence/compute/list',
    method: 'get',
    params: query
  })
}

// 查询【请填写功能名称】详细
export function get(id) {
  return request({
    url: '/data/rentprice/residence/compute/' + id,
    method: 'get'
  })
}

// 修改【请填写功能名称】
export function update(data) {
  return request({
    url: '/data/rentprice/residence/compute',
    method: 'put',
    data: data
  })
}

// 导出【请填写功能名称】
export function export2File(query) {
  return request({
    url: '/data/rentprice/residence/compute/export',
    method: 'get',
    params: query
  })
}

export function getYearMonthList() {
  return request({
    url: '/data/rentprice/residence/compute/yearmonth',
    method: 'get'
  })
}
